package ggitlab.error;

@SuppressWarnings("serial")
public class UnauthorizedException extends Exception {
	
	private static final String EXCEPTION_MESSAGE = "Unauthorized. Signin.";

	public UnauthorizedException() {
		super(EXCEPTION_MESSAGE);
	}
}
