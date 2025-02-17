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
import org.springframework.web.bind.annotation.RestController;

import in.testuniversity.dto.OptionDTO;
import in.testuniversity.service.OptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@Tag(name = "Option manager", description = "API to manage options of a question")
@RestController
@RequestMapping("/api/options")
public class OptionController {

	private final OptionService optionService;
	
	public OptionController(OptionService optionService) {
		this.optionService = optionService;
	}
	//For creating an option
	@Operation(summary = "Create Option", description = "Create option for given question",
			responses = {
					@ApiResponse(responseCode = "201", description = "Option created for a question"),
					@ApiResponse(responseCode = "404", description = "Question not found")
			})
	@PostMapping
	public ResponseEntity<OptionDTO> createOption(@Valid @RequestBody OptionDTO optionDTO){
		OptionDTO createdOption = optionService.createOption(optionDTO);
		return new ResponseEntity<>(createdOption, HttpStatus.CREATED);
	}
	
	//Get all options for a questiopns
	@Operation(summary = "Fetch all option", description = "Fetch all options for given question",
			responses = {
					@ApiResponse(responseCode = "200", description = "Fetched all options for question"),
					@ApiResponse(responseCode = "404", description = "Options not found")
			})
	@GetMapping("/questions/{questionId}/options")
	public ResponseEntity<List<OptionDTO>> getAllOptionsByQuestionId(@PathVariable Long questionId){
		List<OptionDTO> allOptions = optionService.getAllOptionsByQuestion(questionId);
		return ResponseEntity.ok(allOptions);
	}
	
	//Get option by its id
	@Operation(summary = "Fetch option by Id", description = "Fetch option by id",
			responses = {
					@ApiResponse(responseCode = "200", description = "Fetched option with givem Id"),
					@ApiResponse(responseCode = "404", description = "Options not found")
			})
	@GetMapping("/{optionId}")
	public ResponseEntity<OptionDTO> getOptionById(@PathVariable Long optionId){
		OptionDTO option = optionService.getOptionById(optionId);
		return ResponseEntity.ok(option);
	}
	//Method to update an option
	@Operation(summary = "Update option with Id", description = "Update option with given id for a question",
			responses = {
					@ApiResponse(responseCode = "200", description = "Updated option with given id for question"),
					@ApiResponse(responseCode = "404", description = "Option not found")
			})
	@PutMapping("/{optionId}")
	public ResponseEntity<OptionDTO> updateOption(@PathVariable Long optionId, @RequestBody OptionDTO optionDTO){
		OptionDTO updatedOption = optionService.updateOption(optionId, optionDTO);
		return ResponseEntity.ok(updatedOption);
	}
	//Rest method to delete an option
	@Operation(summary = "Delete option with Id", description = "Delete option with given id for a question",
			responses = {
					@ApiResponse(responseCode = "204", description = "Deleted option with given id for question"),
					@ApiResponse(responseCode = "404", description = "Option not found")
			})
	@DeleteMapping("/{optionId}")
	public ResponseEntity<Void> deleteOption(@PathVariable Long optionId){
		optionService.deleteOption(optionId);
		return ResponseEntity.noContent().build();
	}
	//Rest method to mark an option as correct
	@Operation(summary = "Update correct option", description = "Update correct option with given id for a question",
			responses = {
					@ApiResponse(responseCode = "200", description = "Updated correct option with given id for question"),
					@ApiResponse(responseCode = "404", description = "Option not found")
			})
	@PutMapping("/{optionId}/correct")
	public ResponseEntity<OptionDTO> markOptionASCorrect(@PathVariable Long optionId){
		OptionDTO updatedOption = optionService.markAsCorrectOption(optionId);
		return ResponseEntity.ok(updatedOption);
	}
	//Method to get correct option of a question
	@Operation(summary = "Fetch correct option", description = "Fetch correct option for a question",
			responses = {
					@ApiResponse(responseCode = "200", description = "Fetched correct option for a question"),
					@ApiResponse(responseCode = "404", description = "Correct option not found")
			})
	@GetMapping("/questions/{questionId}/correct-option")
	public ResponseEntity<OptionDTO> getCorrectOptionByQuestion(@PathVariable Long questionId){
		OptionDTO correctOption = optionService.getCorrectOptionByQuestion(questionId);
		return ResponseEntity.ok(correctOption);
	}
	
}
