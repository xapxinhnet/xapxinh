package net.xapxinh.center.client.core.rpc;


import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.QueryParam;

import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;

import net.xapxinh.center.shared.cookie.Kookie;

/**
 * @author Sann Tran
 */
public interface LoginService extends RestService {

	@POST
	@Path("authen/kookies/login")
	public void login(MethodCallback<Kookie> callback);

	@POST
	@Path("authen/kookies/login")
	public void login(@QueryParam("playerId") Long playerId,
			@QueryParam("hashPass") String hashPass,
			@QueryParam("remember") Boolean remember,
			@QueryParam("language") String language,
			MethodCallback<Kookie> callback);

	@POST
	@Path("authen/kookies/logout")
	public void logout(MethodCallback<Kookie> callback);

}