package in.testuniversity.exception;

public class OptionNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public OptionNotFoundException(String message) {
		super(message);
	}
}
