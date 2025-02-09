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

@RestController
@RequestMapping("/api/option")
public class OptionController {

	private final OptionService optionService;
	
	public OptionController(OptionService optionService) {
		this.optionService = optionService;
	}
	//For creating an option
	@PostMapping
	public ResponseEntity<OptionDTO> createOption(@RequestBody OptionDTO optionDTO){
		OptionDTO createdOption = optionService.createOption(optionDTO);
		return new ResponseEntity<>(createdOption, HttpStatus.CREATED);
	}
	
	//Get all options for a questiopns
	@GetMapping("/question/{questionId}")
	public ResponseEntity<List<OptionDTO>> getAllOptionsByQuestionId(@PathVariable Long questionId){
		List<OptionDTO> allOptions = optionService.getAllOptionsByQuestion(questionId);
		return ResponseEntity.ok(allOptions);
	}
	
	//Get option by its id
	@GetMapping("{optionId}")
	public ResponseEntity<OptionDTO> getOptionById(@PathVariable Long optionId){
		OptionDTO option = optionService.getOptionById(optionId);
		return ResponseEntity.ok(option);
	}
	//Method to update an option
	@PutMapping("/{optionId}")
	public ResponseEntity<OptionDTO> updateOption(@PathVariable Long optionId, @RequestBody OptionDTO optionDTO){
		OptionDTO updatedOption = optionService.updateOption(optionId, optionDTO);
		return ResponseEntity.ok(updatedOption);
	}
	//Rest method to delete an option
	@DeleteMapping("/{optionId}")
	public ResponseEntity<Void> deleteOption(@PathVariable Long optionId){
		optionService.deleteOption(optionId);
		return ResponseEntity.noContent().build();
	}
	//Rest method to mark an option as correct
	@PutMapping("/{optionId}/markCorrect")
	public ResponseEntity<OptionDTO> markOptionASCorrect(@PathVariable Long optionId){
		OptionDTO updatedOption = optionService.markAsCorrectOption(optionId);
		return ResponseEntity.ok(updatedOption);
	}
	//Method to get correct option of a question
	@GetMapping("/question/{questionId}/correct")
	public ResponseEntity<OptionDTO> getCorrectOptionByQuestion(@PathVariable Long questionId){
		OptionDTO correctOption = optionService.getCorrectOptionByQuestion(questionId);
		return ResponseEntity.ok(correctOption);
	}
	
}
