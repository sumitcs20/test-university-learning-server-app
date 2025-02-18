package in.testuniversity.controller;

import org.springframework.data.domain.Page;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Question Manager", description = "API to manage Questions in a Topic")
@RestController
@RequestMapping("/api/questions")
public class QuestionController {

	private final QuestionService questionService;
	
	public QuestionController(QuestionService questionService) {
		this.questionService = questionService;
	}
	
	//Rest ResponseEntity method to create a new question
	@Operation(summary = "Create Question", description = "Create question in a topic",
			responses = {
					@ApiResponse(responseCode = "201", description = "Question created in topic"),
					@ApiResponse(responseCode = "404", description = "Topic not found to create a question")
			})
	@PostMapping
	public ResponseEntity<QuestionDTO> createQuestion(@Valid @RequestBody QuestionDTO questionDTO){
		QuestionDTO createdQuestion = questionService.createQuestionWithOptions(questionDTO);
		return new ResponseEntity<>(createdQuestion, HttpStatus.CREATED);
	}
	
	//Rest method to fetch all the questions associated with a topic
	@Operation(summary = "Fetch all questions", description = "Fetch all the questions present in a topic",
			responses = {
					@ApiResponse(responseCode = "200", description = "All questions fetched for the topic"),
					@ApiResponse(responseCode = "404", description = "Questions not found")
			})
	@GetMapping("/topics/{topicId}/questions")
	public ResponseEntity<Page<QuestionDTO>> getAllQuestionsByTopic(@PathVariable Long topicId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "questionText") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir){
		
		Page<QuestionDTO> allQuestions = questionService.getAllQuestionsByTopic(topicId, page, size, sortBy, sortDir);
		return ResponseEntity.ok(allQuestions);
	}
	
	//Rest method to fetch a question with question Id
	@Operation(summary = "Fetch question by Id", description = "Fetch question by Id in a topic",
			responses = {
					@ApiResponse(responseCode = "200", description = "Fetched question by Id in a topic"),
					@ApiResponse(responseCode = "404", description = "Question not found")
			})
	@GetMapping("/{id}")
	public ResponseEntity<QuestionDTO> getQuestionById(@PathVariable Long id){
		QuestionDTO question = questionService.getQuestionById(id);
		return ResponseEntity.ok(question);
	}

	//Rest Update method to update an existing question
	@Operation(summary = "Update existing question", description = "Updates existing question of a topic",
			responses = {
					@ApiResponse(responseCode = "200", description = "Updated an existing question"),
					@ApiResponse(responseCode = "404", description = "Question not found")
			})
	@PutMapping("/{id}")
	public ResponseEntity<QuestionDTO> updateQuestion(@Valid @PathVariable Long id, @RequestBody QuestionDTO questionDTO){
		QuestionDTO updatedQuestion = questionService.updateQuestion(id, questionDTO);
		return ResponseEntity.ok(updatedQuestion);
	}
	
	//Rest method to delete a question
	@Operation(summary = "Delete question", description = "Delete existing question of a topic with question id",
			responses = {
					@ApiResponse(responseCode = "204", description = "Deleted question with given Id"),
					@ApiResponse(responseCode = "404", description = "Question not found")
			})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteQuestion(@PathVariable Long id){
		questionService.deleteQuestion(id);
		return ResponseEntity.noContent().build();
	}
	
}
