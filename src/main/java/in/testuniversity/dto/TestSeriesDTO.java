package in.testuniversity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class TestSeriesDTO {

	@NotNull(message = "Test name is required")
	@Size(min = 3, max = 100, message = "Test name should be between 10 to 100 characters")
	private String testName;
	
	@Size(max = 500 ,message = "Test description should not exceed 500 characters")
	private String testDesc;
	
	@NotNull(message = "Stream id is required")
	private Long streamId;

	//Getters Setters
	public String getTestName() {
		return testName;
	}

	public void setTestName(String testName) {
		this.testName = testName;
	}

	public String getTestDesc() {
		return testDesc;
	}

	public void setTestDesc(String testDesc) {
		this.testDesc = testDesc;
	}

	public Long getStreamId() {
		return streamId;
	}

	public void setStreamId(Long streamId) {
		this.streamId = streamId;
	}
	
	
	
}
