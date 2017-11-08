package net.xapxinh.center.server.exception;

public class LoginFailedException extends CenterServerException {

	private static final long serialVersionUID = 1L;

	public LoginFailedException() {
		super();
	}

	public LoginFailedException(String message) {
		super(message);
	}
}
