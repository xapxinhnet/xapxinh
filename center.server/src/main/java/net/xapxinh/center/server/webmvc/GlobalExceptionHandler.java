package net.xapxinh.center.server.webmvc;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class GlobalExceptionHandler {

	private static final  Logger LOGGER = Logger.getLogger(GlobalExceptionHandler.class.getName());

	@ExceptionHandler(value = Throwable.class)
	@ResponseBody
	public ServerMessageResponse defaultErrorHandler(HttpServletRequest request, HttpServletResponse response,
			Throwable e) {
		if (e instanceof ServletException) {
			LOGGER.error(e.getMessage());
		}
		else {
			LOGGER.error(e.getMessage(), e);
		}
		response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
		return new ServerMessageResponse(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
				e.getMessage());
	}
}
