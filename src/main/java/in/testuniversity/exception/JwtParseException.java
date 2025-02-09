package in.testuniversity.exception;

public class JwtParseException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;

	public JwtParseException(String message, Throwable cause) {
		super(message, cause);
	}
}
