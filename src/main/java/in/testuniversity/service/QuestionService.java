package in.testuniversity.service;

import java.util.List;
import in.testuniversity.dto.QuestionDTO;

public interface QuestionService {
	
	//To create question with options
	QuestionDTO createQuestionWithOptions(QuestionDTO questionDTO);
	
	//To fetch all the questions based on the given topic id
	List<QuestionDTO> getAllQuestionsByTopic(Long topicId);
	
	//To fetch a question based on question Id
	QuestionDTO getQuestionById(Long questionId);
	
	//To update a existing question
	QuestionDTO updateQuestion(Long questionId, QuestionDTO questionDTO);
	
	//To delete a question based on given question Id
	boolean deleteQuestion(Long questionId);
	

}
