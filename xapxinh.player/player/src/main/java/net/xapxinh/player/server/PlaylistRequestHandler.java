package net.xapxinh.player.server;

import java.util.Map;

import net.xapxinh.player.EmbeddedMediaPlayerPanel;
import net.xapxinh.player.model.Playlist;

public class PlaylistRequestHandler {
	
	private EmbeddedMediaPlayerPanel mediaPlayerPanel;
	
	public PlaylistRequestHandler(EmbeddedMediaPlayerPanel mediaPlayerPanel) {
		this.mediaPlayerPanel = mediaPlayerPanel;
	}

	public Playlist handleRequest(Map<String, String> params) {
		return getPlaylist();
	}

	private Playlist getPlaylist() {
		return mediaPlayerPanel.getPlaylist();
	}
}
