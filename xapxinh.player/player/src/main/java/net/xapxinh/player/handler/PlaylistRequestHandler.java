package net.xapxinh.player.handler;

import java.util.Map;

import com.google.gson.Gson;

import net.xapxinh.player.EmbeddedMediaPlayerPanel;
import net.xapxinh.player.model.Album;
import net.xapxinh.player.model.PlayList;

public class PlaylistRequestHandler {
	
	private EmbeddedMediaPlayerPanel mediaPlayerPanel;
	private final Gson gson;
	
	public PlaylistRequestHandler(EmbeddedMediaPlayerPanel mediaPlayerPanel) {
		gson = new Gson();
		this.mediaPlayerPanel = mediaPlayerPanel;
	}

	public PlayList handleRequest(Map<String, String> params) {
		
		if (params.isEmpty()) {
			return mediaPlayerPanel.getPlaylist();
		}
		String command = params.get("command");
		if (command == null) {
			return mediaPlayerPanel.getPlaylist();
		}
		
		if ("pl_update".equals(command)) {
			updatePlaylist(params);
		}
		return getPlaylist();
	}

	private void updatePlaylist(Map<String, String> params) {
		String plJson = params.get("playlist");
		PlayList playlist = gson.fromJson(plJson, PlayList.class);
	}

	private PlayList getPlaylist() {
		return mediaPlayerPanel.getPlaylist();
	}
}
