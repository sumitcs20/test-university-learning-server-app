package in.testuniversity.exception;

public class QuestionAlreadyExistsException extends RuntimeException {

	private static final long serialVersionUID = 1l;
	
	public QuestionAlreadyExistsException(String message) {
		super(message);
	}

}
