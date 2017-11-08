package net.xapxinh.center.client.play;

import net.xapxinh.center.client.core.rpc.LoginService;
import net.xapxinh.center.client.player.event.ShowLoadingEvent;
import net.xapxinh.center.client.player.rpc.PlayerService;

import com.google.gwt.event.shared.HandlerManager;
import com.google.inject.Inject;

public class RpcAsynProviderImpl implements IRpcAsynProvider {
	
	@Inject
	private LoginService loginService;
	@Inject
	private PlayerService playerService;
	@Inject
	protected HandlerManager eventBus;
	
	@Override
	public LoginService getLoginService() {
		eventBus.fireEvent(new ShowLoadingEvent(true));
		return loginService;
	}

	@Override
	public PlayerService getPlayerService() {
		eventBus.fireEvent(new ShowLoadingEvent(true));
		return playerService;
	}
}
