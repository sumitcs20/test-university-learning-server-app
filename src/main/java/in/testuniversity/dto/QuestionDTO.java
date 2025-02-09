package in.testuniversity.dto;

import java.util.List;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class QuestionDTO {
	
	@NotNull(message = "Topic ID is required")
	private Long topicId;
	
	@NotNull(message = "Question text is required")
	@Size(min = 10, message = "Question text must be at least 10 characters")
	private String questionText;
	
	@NotNull(message = "options required")
	 private List<OptionDTO> options;
	
	@NotNull
	private Long correctOptionId;

	//Getters Setters
	public Long getTopicId() {
		return topicId;
	}

	public void setTopicId(Long topicId) {
		this.topicId = topicId;
	}

	public String getQuestionText() {
		return questionText;
	}

	public void setQuestionText(String questionText) {
		this.questionText = questionText;
	}

	public List<OptionDTO> getOptions() {
		return options;
	}

	public void setOptions(List<OptionDTO> options) {
		this.options = options;
	}

	public Long getCorrectOptionId() {
		return correctOptionId;
	}

	public void setCorrectOptionId(Long correctOptionId) {
		this.correctOptionId = correctOptionId;
	}

}
