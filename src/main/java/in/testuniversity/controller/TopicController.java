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

import in.testuniversity.dto.TopicDTO;
import in.testuniversity.service.TopicService;

@RestController
@RequestMapping("/api/topics")
public class TopicController {

	private final TopicService topicService;
	
	public TopicController(TopicService topicService) {
		this.topicService = topicService;
	}
	
	//For creating a new Topic
	@PostMapping
	public ResponseEntity<TopicDTO> createTopic(@RequestBody TopicDTO topicDTO){
		TopicDTO createdTopic = topicService.createTopic(topicDTO);
		return new ResponseEntity<>(createdTopic, HttpStatus.CREATED);
	}
	
	//Get all topics for a stream
	@GetMapping("/stream/{streamId}")
	public ResponseEntity<List<TopicDTO>> getAllTopicsByStream(@PathVariable Long streamId){
		List<TopicDTO> topics = topicService.getAllTopicsByStream(streamId);
		return ResponseEntity.ok(topics);
	}
	
	//Fetch topic by topic id
	@GetMapping("/{id}")
	public ResponseEntity<TopicDTO> getTopicById(@PathVariable Long id){
		TopicDTO topic = topicService.getTopicById(id);
		return ResponseEntity.ok(topic);
	}
	
	//To update an existing Topic
	@PutMapping("/{id}")
	public ResponseEntity<TopicDTO> updateTopic(@PathVariable Long id, TopicDTO topicDTO){
		TopicDTO updatedTopic = topicService.updateTopic(id, topicDTO);
		return ResponseEntity.ok(updatedTopic);
	}
	
	//To delete a topic by topic Id
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteTopicById(@PathVariable Long id){
		topicService.deleteTopic(id);
		return ResponseEntity.noContent().build();
	}
}
