package net.xapxinh.player;

import static net.xapxinh.player.Application.application;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.xapxinh.player.event.PausedEvent;
import net.xapxinh.player.event.PlayingEvent;
import net.xapxinh.player.event.StoppedEvent;
import net.xapxinh.player.model.Album;
import net.xapxinh.player.model.Song;
import net.xapxinh.player.model.MediaFile;
import net.xapxinh.player.model.Playlist;
import net.xapxinh.player.model.PlaylistLeaf;
import net.xapxinh.player.model.PlaylistNode;
import net.xapxinh.player.model.State;
import net.xapxinh.player.model.Status;
import net.xapxinh.player.model.YoutubeVideo;
import uk.co.caprica.vlcj.binding.internal.libvlc_media_t;
import uk.co.caprica.vlcj.component.EmbeddedMediaPlayerComponent;
import uk.co.caprica.vlcj.filter.MediaFileFilter;
import uk.co.caprica.vlcj.player.MediaPlayer;

@SuppressWarnings("serial")
public class EmbeddedMediaPlayerPanel extends EmbeddedMediaPlayerComponent {
	
	private final EmbeddedMediaListPlayer mediaListPlayer;
	
	public EmbeddedMediaPlayerPanel() {
		super();
		mediaListPlayer = new EmbeddedMediaListPlayer(getMediaPlayer());
		getMediaPlayer().setRepeat(false);
		getMediaPlayer().setPlaySubItems(true); // <--- This is very important for YouTube media
	}

	@Override
    public void mediaChanged(MediaPlayer mediaPlayer, libvlc_media_t media, String mrl) {
		System.out.println("mediaChanged");
		System.out.println(mrl);
		mediaListPlayer.mediaChanged();
    }

	@Override
    public void mediaStateChanged(MediaPlayer mediaPlayer, int newState) {
		State state = State.getState(newState);
		if (State.playing.equals(state)) {
			System.out.println("playing");
	    	application().post(PlayingEvent.instance());
	    	mediaListPlayer.onPlaying();
		}
		else if (State.stopped.equals(state)) {
			System.out.println("stopped");
	    	application().post(StoppedEvent.instance());
	    	mediaListPlayer.onStopped();
		}
		else if (State.paused.equals(state)) {
			System.out.println("paused");
	    	application().post(PausedEvent.instance());
		}
		else if (State.error.equals(state)) {
			System.out.println("error");
	    	mediaListPlayer.onError();
		}
		else if (State.opening.equals(state)) {
			System.out.println("opening");
		}
    }
	
	@Override
    public void finished(MediaPlayer mediaPlayer) {
		System.out.println("finished");
		mediaListPlayer.onFinished();
    }
    
    @Override
    public void mediaSubItemAdded(MediaPlayer mediaPlayer, libvlc_media_t subItem) {
        System.out.println("mediaSubItemAdded: " + mediaPlayer.mrl(subItem));
    }
	
	public void inEnqueue(MediaFile mediaFile) {
		mediaListPlayer.enqueue(createNode(mediaFile));
	}
	
	public void inEnqueue(YoutubeVideo youtubeVideo) {
		mediaListPlayer.enqueue(createNode(youtubeVideo));
	}
	
	public void inEnqueue(File[] files) {
		if (files.length == 0) {
			return;
		}
		mediaListPlayer.enqueue(createNode(files));
	}
	
	public void inEnqueue(Album album) {
		mediaListPlayer.enqueue(createNode(album));
	}
	
	public void inPlay(MediaFile mediaFile) {
		mediaListPlayer.playNode(createNode(mediaFile));
	}
	
	public void inPlay(File[] files) {
		if (files.length == 0) {
			return;
		}
		mediaListPlayer.playNode(createNode(files));
	}
	
	public void inPlay(Album album) {
		mediaListPlayer.playNode(createNode(album));
	}
	
	public void inPlay(YoutubeVideo youtubeVideo) {
		mediaListPlayer.playNode(createNode(youtubeVideo));
	}
	
	private PlaylistNode createNode(YoutubeVideo youtubeVideo) {
		PlaylistNode node = new PlaylistNode();
		node.setType(PlaylistNode.TYPE.youtube.toString());
		List<PlaylistLeaf> leafs = new ArrayList<PlaylistLeaf>();
		PlaylistLeaf leaf = new PlaylistLeaf();
		leaf.setName(youtubeVideo.getTitle());
		leaf.setType(PlaylistLeaf.TYPE.youtube.toString());
		leaf.setUri(youtubeVideo.getUri());
		leafs.add(leaf);
		node.setLeafs(leafs);
		return node;
	}
	
	private PlaylistNode createNode(MediaFile mediaFile) {
		PlaylistNode node = new PlaylistNode();
		List<PlaylistLeaf> leafs = new ArrayList<PlaylistLeaf>();
		if (MediaFile.TYPE.dir.toString().equals(mediaFile.getType())) {
			File dir = new File(mediaFile.getPath());
			node.setName(dir.getAbsolutePath());
			node.setType(PlaylistNode.TYPE.dir.toString());
			for (File file : dir.listFiles()) {
				if (file.isFile() && MediaFileFilter.INSTANCE.accept(file)) {
					leafs.add(createLeaf(file));
				}
			}
		}
		else {
			File file = new File(mediaFile.getPath());
			node.setName(file.getParentFile().getAbsolutePath());
			node.setType(PlaylistNode.TYPE.dir.toString());
			if (file.isFile() && MediaFileFilter.INSTANCE.accept(file)) {
				leafs.add(createLeaf(file));
			}
		}
		node.setLeafs(leafs);
		return node;
	}

	private PlaylistNode createNode(Album album) {
		PlaylistNode node = new PlaylistNode();
		node.setName(album.getTitle());
		node.setType(PlaylistNode.TYPE.album.toString());
		node.setImage(album.getImage());
		List<PlaylistLeaf> leafs = new ArrayList<PlaylistLeaf>();
		for (Song albumItem : album.getSongs()) {
			leafs.add(createLeaf(albumItem));
		}
		node.setLeafs(leafs);
		return node;
	}
	
	private PlaylistLeaf createLeaf(Song albumItem) {
		PlaylistLeaf leaf = new PlaylistLeaf();
		leaf.setName(albumItem.getTitle());
		leaf.setType(PlaylistLeaf.TYPE.file.toString());
		leaf.setImage(albumItem.getImage());
		leaf.setUri(albumItem.getUri());
		leaf.setArtists(albumItem.getArtists());
		leaf.setAuthor(albumItem.getAuthor());
		return leaf;
	}

	private PlaylistNode createNode(File[] files) {
		File folder = files[0].getParentFile();		
		PlaylistNode node = new PlaylistNode();
		node.setName(folder.getName());
		node.setType(PlaylistNode.TYPE.dir.toString());
		List<PlaylistLeaf> leafs = new ArrayList<PlaylistLeaf>();
		for (File file : files) {
			leafs.add(createLeaf(file));
		}
		node.setLeafs(leafs);
		return node;
	}

	private PlaylistLeaf createLeaf(File file) {
		PlaylistLeaf leaf = new PlaylistLeaf();
		leaf.setName(file.getName());
		leaf.setUri(file.getAbsolutePath());
		leaf.setType(PlaylistLeaf.TYPE.file.toString());
		return leaf;
	}

	public Status getStatus() {
		Status status = new Status();		
		status.setTime(toSecond(getMediaPlayer().getTime()));
		status.setLength(toSecond(getMediaPlayer().getLength()));
		int vol = getMediaPlayer().getVolume();
		if (vol == -1) {
			vol = AppProperties.getVolume();
		}
		status.setVolume(vol);
		status.setPosition(getMediaPlayer().getPosition());
		status.setFullscreen(getMediaPlayer().isFullScreen());
		status.setRandom(mediaListPlayer.isRandom());
		status.setRepeat(mediaListPlayer.isRepeat());
		status.setLoop(mediaListPlayer.isLoop());
		PlaylistNode currentNode = mediaListPlayer.getPlaylist().getCurrentNode();
		status.setPlaylistNode(currentNode);
		if (getMediaPlayer().getMediaState() != null) {
			State state = State.getState(getMediaPlayer().getMediaState().intValue());
			if (state == null) {
				status.setState(State.stopped.toString());
			}
			else {
				status.setState(state.toString());
			}
		}
		else {
			status.setState(State.stopped.toString());
		}
		
		return status;
	}
	
	private long toSecond(long timeMilliSeconds) {
		return timeMilliSeconds/1000;
	}

	public Playlist getPlaylist() {
		return mediaListPlayer.getPlaylist();
	}

	public EmbeddedMediaListPlayer getMediaListPlayer() {
		return mediaListPlayer;
	}

	public void removePlaylistLeaf(long leafId) {
		mediaListPlayer.removeLeaf(leafId);
	}
	
	public void removePlaylistNode(long nodeId) {
		mediaListPlayer.removeNode(nodeId);
	}

	public void playPlaylistLeaf(long id) {
		mediaListPlayer.playLeaf(id);
	}

	public void playPlaylistNode(long id) {
		mediaListPlayer.playNode(id);
	}

	public void playPlaylist() {
		mediaListPlayer.playPlaylist();
	}

	public void repeatPlaylist() {
		mediaListPlayer.repeat();
	}

	public void randomPlaylist() {
		mediaListPlayer.random();
	}

	public void loopPlaylist() {
		mediaListPlayer.loop();
	}

	public void emptyPlaylist() {
		mediaListPlayer.empty();
	}

	public void stop() {
		mediaListPlayer.stop();
	}

	public void pause() {
		mediaListPlayer.pause();
	}

	public void resume() {
		mediaListPlayer.resume();
	}

	public void seekForward() {
		if (getMediaPlayer().isPlaying()) {
			float currentPosition = getMediaPlayer().getPosition();
			getMediaPlayer().setPosition((float) (currentPosition + 0.1));
		}
	}

	public void seekReward() {
		if (getMediaPlayer().isPlaying()) {
			float currentPosition = getMediaPlayer().getPosition();
			getMediaPlayer().setPosition((float) (currentPosition - 0.1));
		}
	}

}
