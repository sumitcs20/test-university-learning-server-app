package in.testuniversity.exception;

public class TopicAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public TopicAlreadyExistsException(String message) {
		super(message);
	}
}
