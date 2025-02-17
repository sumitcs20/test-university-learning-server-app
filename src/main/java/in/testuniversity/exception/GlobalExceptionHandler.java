package in.testuniversity.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	private static final Logger LOGGER = LogManager.getLogger(GlobalExceptionHandler.class);
	
	/*
	 * Handles Entity not found Exceptions
	 */
	@ExceptionHandler({UserNotFoundException.class, StreamNotFoundException.class, TopicNotFoundException.class, QuestionNotFoundException.class, OptionNotFoundException.class })
	public ResponseEntity<ErrorResponse> handleNotFoundException(RuntimeException ex, WebRequest request){
		LOGGER.warn("Resource not found: {}", ex.getMessage()); //404 Not found
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.NOT_FOUND.value(),
				"Not Found",
				ex.getMessage(),
				request.getDescription(false)
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
	}
	
	/*
	 * Handles Entity Already Exists Exceptions
	 */
	
	@ExceptionHandler({UserAlreadyExistsException.class, StreamAlreadyExistsException.class, TopicAlreadyExistsException.class, QuestionAlreadyExistsException.class, OptionAlreadyExistsException.class})
	public ResponseEntity<ErrorResponse> handleAlreadyExistsException(RuntimeException ex, WebRequest request){
		LOGGER.warn("Conflict: ",ex.getMessage()); //409 conflict
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.CONFLICT.value(), 
				"Already Exists",
				ex.getMessage(), 
				request.getDescription(false)
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
	}
	
	
    /*
     * Handles Authentication Failure (Invalid Credentials)
     */
    @ExceptionHandler(BadCredentialsException.class)
    public ResponseEntity<ErrorResponse> handleBadCredentialsException(BadCredentialsException ex, WebRequest request) {
        LOGGER.warn("Authentication failed: {}", ex.getMessage()); // 401 Unauthorized
        ErrorResponse errorResponse = new ErrorResponse(
                LocalDateTime.now(),
                HttpStatus.UNAUTHORIZED.value(),
                "Unauthorized",
                "Invalid credentials. Please try again.",
                request.getDescription(false)
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);
    }
	
	/*
	 * Handles Mapping Exception(DTO to Entity and Entity to DTO)
	 */
	@ExceptionHandler(MappingException.class)
	public ResponseEntity<ErrorResponse> handleMappingException(MappingException ex, WebRequest request){
		LOGGER.error("Mapping failed: {}", ex.getMessage());
		
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(), 
				HttpStatus.BAD_REQUEST.value(),
				"Bad Request",
				ex.getMessage(),
				request.getDescription(false)
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
	}
	
	/*
	 * Handles Validation errors in @RequestBody
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleValidationException(MethodArgumentNotValidException ex){
		LOGGER.warn("Validation Error: {}", ex); //400 validation error
		Map<String, String> errors = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			if(error instanceof FieldError fieldError) {
				errors.put(fieldError.getField(), fieldError.getDefaultMessage());
			}
		});
		return ResponseEntity.status(HttpStatus.BAD_REQUEST)
				.body(errors);
	}	
	
	/*
	 * Handles Generic Exception
	 */
	
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorResponse> handleGlobalException(Exception ex, WebRequest request){
		LOGGER.error("An unexpected error occured: {}", ex.getMessage());
		
		ErrorResponse errorResponse = new ErrorResponse(
				LocalDateTime.now(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), 
				"Internal Server Error",
				ex.getMessage(),
				request.getDescription(false)		
		);
		return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

}
