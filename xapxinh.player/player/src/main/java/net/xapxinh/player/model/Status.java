package net.xapxinh.player.model;

import java.util.ArrayList;

public class Status {

	private boolean fullscreen;
	private String apiversion;
	private long time;
	private int volume;
	private long length;
	private boolean random;
	private String rate;
	private String state;
	private boolean loop;
	private String version;
	private float position;
	private boolean repeat;
	private PlaylistNode playlistNode;
	
	public boolean getFullscreen() {
		return fullscreen;
	}

	public void setFullscreen(boolean fullScreen) {
		this.fullscreen = fullScreen;
	}

	public String getApiversion() {
		return apiversion;
	}

	public void setApiversion(String apiVersion) {
		this.apiversion = apiVersion;
	}

	public long getTime() {
		return time;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public int getVolume() {
		return volume;
	}

	public void setVolume(int volume) {
		this.volume = volume;
	}

	public long getLength() {
		return length;
	}

	public void setLength(long length) {
		this.length = length;
	}

	public boolean getRandom() {
		return random;
	}

	public void setRandom(boolean random) {
		this.random = random;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public boolean getLoop() {
		return loop;
	}

	public void setLoop(boolean loop) {
		this.loop = loop;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	public float getPosition() {
		return position;
	}

	public void setPosition(float position) {
		this.position = position;
	}

	public boolean getRepeat() {
		return repeat;
	}

	public void setRepeat(boolean repeat) {
		this.repeat = repeat;
	}

	public PlaylistNode getPlaylistNode() {
		return playlistNode;
	}

	public void setPlaylistNode(PlaylistNode playlistNode) {
		PlaylistNode node = null;
		if (playlistNode != null) {
			node = new PlaylistNode();
			node.setType(playlistNode.getType());
			node.setName(playlistNode.getName());
			node.setImage(playlistNode.getImage());
			node.setLeafs(new ArrayList<PlaylistLeaf>());
			for (PlaylistLeaf leaf : playlistNode.getLeafs()) {
				if (leaf.isCurrent()) {
					node.getLeafs().add(leaf);
					break;
				}
			}
		}
		this.playlistNode = node;
	}
}
