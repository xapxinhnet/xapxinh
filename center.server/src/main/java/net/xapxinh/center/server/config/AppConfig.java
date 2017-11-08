package net.xapxinh.center.server.config;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

import org.apache.log4j.Logger;

public class AppConfig {
	

	static Logger logger = Logger.getLogger(AppConfig.class.getName());

	private static AppConfig INSTANCE = null;

	public final String DATASERVICE_URL;
	public final int PLAYER_TCP_PORT;
	
	public AppConfig() {
		Properties prop = new Properties();
		try {
			InputStream stream = Thread.currentThread().getContextClassLoader().getResourceAsStream("config.properties");
			InputStreamReader isr = new InputStreamReader(stream, "UTF-8");
			prop.load(isr);
		}
		catch (IOException e) {
			logger.error(e.getMessage(), e);
		}

		DATASERVICE_URL = prop.getProperty("dataservice_url");
		int port = 39994;
		try {
			port = Integer.parseInt(prop.getProperty("tcp_port"));
		}
		catch (NumberFormatException e) {
			logger.info("Player_tcp_port is not set. Use default port");
		}
		PLAYER_TCP_PORT = port;
	}

	public static AppConfig getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new AppConfig();
		}
		return INSTANCE;
	}

	public static boolean isWinPlatform() {
		String os = System.getProperty("os.name");
		if (os.contains("Win")) {
			return true;
		}
		else
			return false;
	}

	public static boolean isLinuxPlatform() {
		String os = System.getProperty("os.name");
		if (os.contains("nux")) {
			return true;
		}
		else
			return false;
	}
}
