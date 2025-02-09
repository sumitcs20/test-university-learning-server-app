package in.testuniversity.exception;

public class OptionAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OptionAlreadyExistsException(String message) {
		super(message);
	}
}
