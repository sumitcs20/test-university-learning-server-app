package in.testuniversity.exception;

public class StreamAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public StreamAlreadyExistsException(String message) {
		super(message);
	}
}
