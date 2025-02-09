package in.testuniversity.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import in.testuniversity.dto.QuestionDTO;
import in.testuniversity.service.QuestionService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/questions")
public class QuestionController {

	private final QuestionService questionService;
	
	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}
	
	//Rest ResponseEntity method to create a new question
	@PostMapping
	public ResponseEntity<QuestionDTO> createQuestion(@Valid @RequestBody QuestionDTO questionDTO){
		QuestionDTO createdQuestion = questionService.createQuestionWithOptions(questionDTO);
		return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
	}
	
	//Rest method to fetch all the questions associated with a topic
	@GetMapping("/topic/questions")
	public ResponseEntity<List<QuestionDTO>> getAllQuestionsByTopic(@RequestParam Long topicId){
		List<QuestionDTO> allQuestions = questionService.getAllQuestionsByTopic(topicId);
		return ResponseEntity.ok(allQuestions);
	}
	
	//Rest method to fetch a question with question Id
	@GetMapping("/{id}")
	public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id){
		QuestionDTO question = questionService.getQuestionById(id);
		return ResponseEntity.ok(question);
	}

	//Rest Update method to update an existing question
	@PutMapping("/{id}")
	public ResponseEntity<QuestionDTO> updateQuestion(@PathVariable Long id, @RequestBody QuestionDTO questionDTO){
		QuestionDTO updatedQuestion = questionService.updateQuestion(id, questionDTO);
		return ResponseEntity.ok(updatedQuestion);
	}
	
	//Rest method to delete a question
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
		questionService.deleteQuestion(id);
		return ResponseEntity.noContent().build();
	}
	
}
