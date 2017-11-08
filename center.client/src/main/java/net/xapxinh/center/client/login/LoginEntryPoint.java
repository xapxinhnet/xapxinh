package net.xapxinh.center.client.login;

import org.fusesource.restygwt.client.RestServiceProxy;

import net.xapxinh.center.client.core.rpc.LoginService;
import net.xapxinh.center.client.core.rpc.ServiceApiUrl;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.HandlerManager;

public class LoginEntryPoint implements EntryPoint {

	@Override
	public void onModuleLoad() {
		final LoginService loginService = GWT.create(LoginService.class);
		final org.fusesource.restygwt.client.Resource resource
		= new org.fusesource.restygwt.client.Resource(ServiceApiUrl.get());
		((RestServiceProxy)loginService).setResource(resource);
		final HandlerManager eventBus = new HandlerManager(null);

		LoginApp loginApp = new LoginApp(loginService, eventBus);
		loginApp.go();
	}
}
