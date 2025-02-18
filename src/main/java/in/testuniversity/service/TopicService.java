package in.testuniversity.service;

import org.springframework.data.domain.Page;

import in.testuniversity.dto.TopicDTO;
import in.testuniversity.exception.TopicAlreadyExistsException;
import in.testuniversity.exception.TopicNotFoundException;

public interface TopicService {
	
	// To create a new topic
	TopicDTO createTopic(TopicDTO topicDTO) throws TopicAlreadyExistsException; 
	
	//To fetch all the topics available based on Stream Id
	Page<TopicDTO> getAllTopicsByStream(Long streamId, int page, int size, String sortBy, String sortDir);
	
	//To fetch topic based on Topic Id
	TopicDTO getTopicById(Long topicId) throws TopicNotFoundException;
	
	//To update a topic based on topic id
	TopicDTO updateTopic(Long topicId, TopicDTO topicDTO);
	
	//To delete the topic based on the given topic id
	boolean deleteTopic(Long topicId);
	
}
