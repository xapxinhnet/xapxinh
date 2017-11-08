package net.xapxinh.center.server.webmvc;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
 
import javax.servlet.http.HttpSession;

import net.xapxinh.center.server.listener.SessionCollection;
import net.xapxinh.center.shared.cookie.Kookie;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class AuthorizedInterceptor extends HandlerInterceptorAdapter {
	
    private static final Logger LOGGER = LoggerFactory
            .getLogger(AuthorizedInterceptor.class);
 
    @Override
    public boolean preHandle(HttpServletRequest request,
            HttpServletResponse response, Object handler) throws IOException {
		 Kookie kookie = HttpServletRequestUtil.getCookie(request);
		 HttpSession httpSession = SessionCollection.getSession(kookie, request);
		 if (httpSession == null) {
			 response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			 response.getWriter().println(getUnauthorizedMessage());
			 LOGGER.error("Request is unauthorized");
			 return false;
		 }
		 request.setAttribute("locale", kookie.getLanguage());
		 return true;
    }
 
    private String getUnauthorizedMessage() {
		return "{\"status\":" + HttpServletResponse.SC_UNAUTHORIZED + ",\"message\":\"Request is unauthorized\" }";
	}

	@Override
    public void afterCompletion(HttpServletRequest request,
            HttpServletResponse response, Object handler, Exception ex) {
    	// does nothing
    }
}