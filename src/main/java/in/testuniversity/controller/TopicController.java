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

import in.testuniversity.dto.TopicDTO;
import in.testuniversity.service.TopicService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Topic Manager", description = "API to manage Topics in a stream")
@RestController
@RequestMapping("/api/topics")
public class TopicController {

	private final TopicService topicService;
	
	public TopicController(TopicService topicService) {
		this.topicService = topicService;
	}
	
	//For creating a new Topic
	@Operation(summary = "Create a topic", description = "create topic in a stream",
			responses = {
					@ApiResponse(responseCode = "201", description = "Topic created in a Stream"),
					@ApiResponse(responseCode = "404", description = "Stream not found to create topic")
			})
	@PostMapping
	public ResponseEntity<TopicDTO> createTopic(@RequestBody TopicDTO topicDTO){
		TopicDTO createdTopic = topicService.createTopic(topicDTO);
		return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
	}
	
	//Get all topics for a stream
	@Operation(summary = "Fetch all topic of stream", description = "Fetch all the topics present in a stream",
			responses = {
					@ApiResponse(responseCode = "200", description = "Fetched all topics")
			})
	@GetMapping("/stream/{streamId}")
	public ResponseEntity<Page<TopicDTO>> getAllTopicsByStream(@PathVariable Long streamId,
			@RequestParam(defaultValue = "0") int page,
			@RequestParam(defaultValue = "10") int size,
			@RequestParam(defaultValue = "topicName") String sortBy,
			@RequestParam(defaultValue = "asc") String sortDir){
		
		Page<TopicDTO> topics = topicService.getAllTopicsByStream(streamId, page, size, sortBy, sortDir);
		return ResponseEntity.ok(topics);
	}
	
	//Fetch topic by topic id
	@Operation(summary = "Fetch topic by Id", description = "Fetch topic by topic id",
			responses = {
					@ApiResponse(responseCode = "200", description = "Topic fetched with Id"),
					@ApiResponse(responseCode = "404", description = "Topic not found with Id")
			})
	@GetMapping("/{id}")
	public ResponseEntity<TopicDTO> getTopicById(@PathVariable Long id){
		TopicDTO topic = topicService.getTopicById(id);
		return ResponseEntity.ok(topic);
	}
	
	//To update an existing Topic
	@Operation(summary = "Update Topic", description = "Update an existing Topic",
			responses = {
					@ApiResponse(responseCode = "200", description = "Topic updated successfully"),
					@ApiResponse(responseCode = "404", description = "Topic not found")
			})
	@PutMapping("/{id}")
	public ResponseEntity<TopicDTO> updateTopic(@PathVariable Long id, TopicDTO topicDTO){
		TopicDTO updatedTopic = topicService.updateTopic(id, topicDTO);
		return ResponseEntity.ok(updatedTopic);
	}
	
	//To delete a topic by topic Id
	@Operation(summary = "Delete Topic", description = "Delete an existing Topic",
			responses = {
					@ApiResponse(responseCode = "204", description = "Topic deleted successfully"),
					@ApiResponse(responseCode = "404", description = "Topic not found")
			})
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTopicById(@PathVariable Long id){
		topicService.deleteTopic(id);
		return ResponseEntity.noContent().build();
	}
}
