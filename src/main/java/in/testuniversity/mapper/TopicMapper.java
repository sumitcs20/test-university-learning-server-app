package in.testuniversity.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import in.testuniversity.dto.TopicDTO;
import in.testuniversity.entity.Stream;
import in.testuniversity.entity.Topic;
import in.testuniversity.exception.MappingException;

@Component
public class TopicMapper implements BaseMapper<Topic, TopicDTO> {

	private static final Logger LOGGER = LogManager.getLogger(TopicMapper.class);
	
	@Override
	public Topic dtoToEntity(TopicDTO topicDTO) throws MappingException {
		if(topicDTO == null) {
			LOGGER.error("Mapping failed: Input Topic DTO is null");
			throw new MappingException("Input Topic DTO cannot be null during mapping to Topic entity.");
		}
		Topic topic = new Topic();
		topic.setTopicName(topicDTO.getTopicName());
		
		// Stream entity object created to map the values
        Stream stream = new Stream();
        if (topicDTO.getStreamId() != null) {
            stream.setId(topicDTO.getStreamId());
            topic.setStream(stream);
        } else {
        	LOGGER.error("Mapping failed: Stream ID in TopicDTO is null");
            throw new MappingException("Stream ID in TopicDTO cannot be null.");
        }
		
		return topic;
	}
	
	@Override
	public TopicDTO entityToDTO(Topic topic) throws MappingException {
		if(topic == null) {
			LOGGER.error("Mapping failed: Input Topic entity is null");
            throw new MappingException("Topic entity cannot be null during mapping to TopicDTO.");

		}
		TopicDTO topicDTO = new TopicDTO();
		topicDTO.setTopicName(topic.getTopicName());
		
		 if (topic.getStream() != null && topic.getStream().getId() != null) {
	         topicDTO.setStreamId(topic.getStream().getId());
	     } else {
	    	 LOGGER.error("Mapping failed: Stream ID in Topic entity is null");
	           throw new MappingException("Stream ID in topic entity cannot be null.");
	     }
		return topicDTO;
	}

}
