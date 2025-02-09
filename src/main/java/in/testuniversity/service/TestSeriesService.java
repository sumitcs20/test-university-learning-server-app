package in.testuniversity.service;

import java.util.List;

import in.testuniversity.dto.QuestionDTO;
import in.testuniversity.dto.StreamDTO;
import in.testuniversity.dto.TopicDTO;

public interface TestSeriesService {
	
	List<StreamDTO> getAllStreams(); //Fetch all streams available
	
	List<TopicDTO> getTopicsByStream(Long streamId); // Fetch all topics related to stream selected
	
	List<QuestionDTO> getQuestionsByTopics(Long topicId); //Fetch all questions based on topics
	
	
	//Admin- add topic to a Stream
	TopicDTO addTopicToStream(Long streamId, TopicDTO topicDTO);
	
	// Admin- add question to a topic
	QuestionDTO addQuestionToTopic(Long topicId, QuestionDTO questionDTO);

	   // Admin operations - Delete a topic (and its related questions)
    void deleteTopic(Long topicId);

    // Admin operations - Delete a question
    void deleteQuestion(Long questionId);
}
