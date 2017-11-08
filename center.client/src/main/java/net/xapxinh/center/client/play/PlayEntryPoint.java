package net.xapxinh.center.client.play;

import com.google.gwt.core.client.EntryPoint;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class PlayEntryPoint implements EntryPoint {
	
	public void onModuleLoad() {
		PlayInjector.INSTANCE.getClientApp().go();
	}
}
