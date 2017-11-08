package net.xapxinh.center.client.play;

import javax.inject.Singleton;

import net.xapxinh.center.client.core.cookie.CookieService;
import net.xapxinh.center.client.core.cookie.CookieServiceImpl;
import net.xapxinh.center.client.core.rpc.LoginService;
import net.xapxinh.center.client.core.rpc.ServiceApiUrl;
import net.xapxinh.center.client.player.rpc.PlayerService;

import org.fusesource.restygwt.client.RestServiceProxy;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;
import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Provider;

public class PlayInjectorModule extends AbstractGinModule {

	@Override
    protected void configure() {

        bind(HandlerManager.class).toProvider(HandlerManagerProvider.class).in(Singleton.class);
        bind(CookieService.class).to(CookieServiceImpl.class).in(Singleton.class);
        bind(LoginService.class).toProvider(LoginServiceProvider.class).in(Singleton.class);
        bind(PlayerService.class).toProvider(PlayerServiceProvider.class).in(Singleton.class);
        bind(IPlayAppController.class).to(PlayAppControllerImpl.class).in(Singleton.class);
        bind(IRpcAsynProvider.class).to(RpcAsynProviderImpl.class).in(Singleton.class);
        bind(IPlayEventHandler.class).to(PlayEventHandlerImpl.class).in(Singleton.class);
        bind(IPlayApp.class).to(PlayAppImpl.class).in(Singleton.class);
    }

    public static class HandlerManagerProvider implements Provider<HandlerManager> {
		@Override
		public HandlerManager get() {
			return new HandlerManager(null);
		}
    }

    public static class LoginServiceProvider implements Provider<LoginService> {
		@Override
		public LoginService get() {
			final org.fusesource.restygwt.client.Resource resource
			= new org.fusesource.restygwt.client.Resource(ServiceApiUrl.get());
			final LoginService service = GWT.create(LoginService.class);;
			((RestServiceProxy)service).setResource(resource);
			return service;
		}
    }
    public static class PlayerServiceProvider implements Provider<PlayerService> {
		@Override
		public PlayerService get() {
			final org.fusesource.restygwt.client.Resource resource
			= new org.fusesource.restygwt.client.Resource(ServiceApiUrl.get());
			final PlayerService service = GWT.create(PlayerService.class);;
			((RestServiceProxy)service).setResource(resource);
			return service;
		}
    }
}
