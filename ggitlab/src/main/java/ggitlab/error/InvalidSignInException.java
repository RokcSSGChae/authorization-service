package ggitlab.error;

@SuppressWarnings("serial")
public class InvalidSignInException extends Exception {
	
	public static final String EXCEPTION_MESSAGE = "Invalid Login or password.";

	public InvalidSignInException() {
		super(EXCEPTION_MESSAGE);
	}
}
