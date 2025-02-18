package in.testuniversity.service;

import org.springframework.data.domain.Page;

import in.testuniversity.dto.QuestionDTO;

public interface QuestionService {
	
	//To create question with options
	QuestionDTO createQuestionWithOptions(QuestionDTO questionDTO);
	
	//To fetch all the questions based on the given topic id
	Page<QuestionDTO> getAllQuestionsByTopic(Long topicId, int page, int size,  String sortBy, String sortDir);
	
	//To fetch a question based on question Id
	QuestionDTO getQuestionById(Long questionId);
	
	//To update a existing question
	QuestionDTO updateQuestion(Long questionId, QuestionDTO questionDTO);
	
	//To delete a question based on given question Id
	boolean deleteQuestion(Long questionId);
	

}
