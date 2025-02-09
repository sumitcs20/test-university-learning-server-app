package in.testuniversity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class StreamDTO {
	
	@NotNull(message = "Stream name is required.")
	@Size(min = 3, max = 100, message = "Stream name must be between 3 to 100 characters")
	private String streamName;

	//Getters Setters
	public String getStreamName() {
		return streamName;
	}

	public void setStreamName(String streamName) {
		this.streamName = streamName;
	}

	
}
