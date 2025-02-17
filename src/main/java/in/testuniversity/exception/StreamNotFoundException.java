package in.testuniversity.exception;

public class StreamNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StreamNotFoundException(String message) {
		super(message);
	}
}
