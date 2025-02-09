package in.testuniversity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
	
	@NotNull(message = "Topic name is required")
	@Size(min = 3, max = 100, message = "Topic name should be between 3 to 100 characters")
	private String topicName;
	
	@NotNull(message = "Stream ID is required")
	private Long streamId;
	
	//Getters and Setters
	public String getTopicName() {
		return topicName;
	}

	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	public Long getStreamId() {
		return streamId;
	}

	public void setStreamId(Long streamId) {
		this.streamId = streamId;
	}

}
