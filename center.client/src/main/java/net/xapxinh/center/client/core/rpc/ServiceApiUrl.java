package net.xapxinh.center.client.core.rpc;

import com.google.gwt.user.client.Window;

public class ServiceApiUrl {
	public static String get() {
    	return Window.Location.getProtocol() + "//" 
    				+ Window.Location.getHostName() + ":" 
    				+ Window.Location.getPort() + "/xserver";
    }
}
