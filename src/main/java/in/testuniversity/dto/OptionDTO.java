package in.testuniversity.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class OptionDTO {
	
	@NotNull(message = "Option Text is required")
	@Size(min = 1, max = 255, message = "Option must be betwweb 1 to 255 characters")
	private String optionText;
	
	@NotNull(message = "Question ID is required")
	private Long questionId;
	
	@NotNull
	private Boolean isCorrect;

	//Getters and Setters
	public String getOptionText() {
		return optionText;
	}

	public void setOptionText(String optionText) {
		this.optionText = optionText;
	}

	public Long getQuestionId() {
		return questionId;
	}

	public void setQuestionId(Long questionId) {
		this.questionId = questionId;
	}

	public Boolean getIsCorrect() {
		return isCorrect;
	}

	public void setIsCorrect(Boolean isCorrect) {
		this.isCorrect = isCorrect;
	}
	

}
