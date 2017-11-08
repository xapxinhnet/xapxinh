package net.xapxinh.center.client.play;

import net.xapxinh.center.client.core.rpc.LoginService;
import net.xapxinh.center.client.player.rpc.PlayerService;

public interface IRpcAsynProvider {
	LoginService getLoginService();
	PlayerService getPlayerService();
}
