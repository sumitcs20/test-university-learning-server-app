package in.testuniversity.exception;

import java.time.LocalDateTime;

/*
 * ErrorResponse model for consistent error response
 */
public class ErrorResponse {
	
	private LocalDateTime timeStamp;
	private int status;
	private String error;
	private String message;
	private String path;
	
	public ErrorResponse(LocalDateTime timeStamp, int status, String error, String message, String path) {
		this.timeStamp = timeStamp;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	//Getters Setters
	public LocalDateTime getTimeStamp() {
		return timeStamp;
	}

	public void setTimeStamp(LocalDateTime timeStamp) {
		this.timeStamp = timeStamp;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}
}
