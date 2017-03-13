package net.xapxinh.player;

import static net.xapxinh.player.Application.application;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

import org.apache.log4j.Logger;

import net.xapxinh.player.event.ErrorEvent;
import net.xapxinh.player.event.FinishedEvent;
import net.xapxinh.player.model.Playlist;
import net.xapxinh.player.model.PlaylistLeaf;
import net.xapxinh.player.model.PlaylistNode;
import net.xapxinh.player.model.State;
import uk.co.caprica.vlcj.player.embedded.EmbeddedMediaPlayer;

public class EmbeddedMediaListPlayer {
	
	private static final Logger LOGGER = Logger.getLogger(EmbeddedMediaListPlayer.class.getName());
	
	private boolean random;
	private PlaylistMode mode;
	private PlaylistLeaf current;
	private final Playlist playlist;
	private final EmbeddedMediaPlayer mediaPlayer;
	private State state;
	 
	EmbeddedMediaListPlayer(EmbeddedMediaPlayer mediaPlayer) {
		this.mediaPlayer = mediaPlayer;
		mediaPlayer.setRepeat(true);
		playlist = new Playlist();
		playlist.setNodes(new ArrayList<PlaylistNode>());
		setMode(PlaylistMode.DEFAULT);
	}
	
	PlaylistMode getMode() {
		return mode;
	}

	void setMode(PlaylistMode mode) {
		this.mode = mode;
	}
	
	void enqueue(PlaylistNode node) {
		addNode(node);
	}
	
	void playNode(PlaylistNode node) {
		if (!node.hasLeaf()) {
			playLeaf(null);
		}
		addNode(node);
		playLeaf(node.getLeafs().get(0));
	}

	private void playLeaf(PlaylistLeaf leaf) {
		mediaPlayer.stop(); // stop to avoid LoggingSubscriberExceptionHandler
		current = leaf;
		if (leaf != null) {
			current.setPlayed(false);
			current.setCurrent(true);
			mediaPlayer.playMedia(current.getUri());
		}
	}
	
	void onPlaying() {
		state = State.playing;
		if (current != null && !current.isCurrent()) {
			current.setCurrent(true);
		}
	}
	
	void onStopped() {
		state = State.stopped;
		playlist.resetCurrent();
	}

	private PlaylistLeaf getNext(boolean autoPlay) {
		if (playlist.isEmpty()) {
			return null;
		}
		if (PlaylistMode.LOOP.toString().equals(mode) && playlist.isFinished()) {
			playlist.resetPlayed();
		}
		if (PlaylistMode.REPEAT.toString().equals(mode) && autoPlay) {
			return current;
		}
		if (random) {
			if (!autoPlay && playlist.getRandomUnPlayedLeaf() == null) {
				playlist.resetPlayed();
			}
			return playlist.getRandomUnPlayedLeaf();
		}
		else {
			long id = 0;
			if (current != null) {
				id = current.getId();
			}
			if (!autoPlay && playlist.getDefaultUnPlayedLeaf(id) == null) {
				playlist.resetPlayed();
			}
			return playlist.getDefaultUnPlayedLeaf(id);
		}
	}
	
	private void addNode(PlaylistNode node) {
		if (playlist.hasNode(node)) {
			return;
		}
		long maxNodeId = playlist.getNodeIndex();
		node.setId(maxNodeId + 1);
		long maxLeafId = playlist.getLeafIndex();
		int nodeLeafs = node.getLeafs().size();
		for (int i = 0; i < nodeLeafs; i++) {
			long leafId = maxLeafId + i + 1;
			node.getLeafs().get(i).setId(leafId);
			playlist.setLeafIndex(leafId);
		}
		playlist.getNodes().add(node);
		playlist.setNodeIndex(node.getId());
	}

	void setRandom(boolean random) {
		this.random = random;
	}
	
	boolean isRandom() {
		return random;
	}
	
	boolean isRepeat() {
		return PlaylistMode.REPEAT.equals(mode);
	}
	
	boolean isLoop() {
		return PlaylistMode.LOOP.equals(mode);
	}

	public Playlist getPlaylist() {
		return playlist;
	}

	public void playNext() {
		if (current != null) {
			current.setPlayed(true);
		}
		playLeaf(getNext(false));
	}

	public void playPrevious() {
		if (current != null) {
			current.setPlayed(false);
		}
		playLeaf(getPrev(false));
	}
	
	public PlaylistLeaf getCurent() {
		return current;
	}

	private PlaylistLeaf getPrev(boolean autoPlay) {
		if (playlist.isEmpty()) {
			return null;
		}
		if (PlaylistMode.LOOP.toString().equals(mode) && playlist.isFinished()) {
			playlist.resetPlayed();
		}
		if (PlaylistMode.REPEAT.toString().equals(mode) && autoPlay) {
			return current;
		}
		if (random) {
			return playlist.getRandomPlayedLeaf();
		}
		else {
			return playlist.getDefaultPlayedLeaf();
		}
	}

	public void removeLeaf(long leafId) {
		if (current != null && current.getId() == leafId) {
			stop();
		}
		playlist.removeLeaf(leafId);
	}
	

	public void removeNode(long nodeId) {
		PlaylistNode node = playlist.getNode(nodeId);
		if (node != null && node.isCurrent()) {
			stop();
		}
		playlist.removeNode(nodeId);
	}
	

	public void mediaChanged() {
		if (State.opening.equals(state) || State.stopped.equals(state)) {
			mediaPlayer.play();
		}
	}

	public void onFinished() {
		state = State.finished;
		application().post(FinishedEvent.instance());
		current.setPlayed(true);
		playLeaf(getNext(true));
	}

	public void onError() {
		state = State.error;
		if (current != null && current.isYoutube()) {
			return; // don't care this case
		}
		application().post(ErrorEvent.instance());
		current.setPlayed(true);
		playLeaf(getNext(true));
	}

	void playLeaf(long id) {
		PlaylistLeaf leaf = playlist.getLeaf(id);
		playLeaf(leaf);
	}

	public void playNode(long id) {
		PlaylistNode node = playlist.getNode(id);
		playNode(node);
	}

	public void playPlaylist() {
		if (!playlist.getLeafs().isEmpty()) {
			playlist.resetPlayed();
			playLeaf(playlist.getLeafs().get(0));
		}
	}

	public void repeat() {
		if (isRepeat()) {
			mode = PlaylistMode.DEFAULT;
		}
		else {
			mode = PlaylistMode.REPEAT;
		}
	}
	
	public void loop() {
		if (isLoop()) {
			mode = PlaylistMode.DEFAULT;
		}
		else {
			mode = PlaylistMode.LOOP;
		}
	}

	public void random() {
		if (isRandom()) {
			random = false;
		}
		else {
			random = true;
		}
	}

	public void empty() {
		mediaPlayer.stop();
		playlist.clear();
	}

	public void stop() {
		mediaPlayer.stop();
	}

	public void pause() {
		mediaPlayer.setPause(true);
	}

	public void resume() {
		mediaPlayer.play();
	}

	public EmbeddedMediaPlayer getMediaPlayer() {
		return mediaPlayer;
	}
}
