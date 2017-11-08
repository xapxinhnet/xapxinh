package net.xapxinh.center.client.play;

import com.google.gwt.core.client.GWT;
import com.google.gwt.inject.client.GinModules;
import com.google.gwt.inject.client.Ginjector;

@GinModules(PlayInjectorModule.class)
public interface PlayInjector extends Ginjector {
	
    public static final PlayInjector INSTANCE = GWT.create(PlayInjector.class);

    public IPlayApp getClientApp();
}
