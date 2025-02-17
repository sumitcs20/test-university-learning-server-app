package in.testuniversity.service.impl;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import in.testuniversity.dto.TopicDTO;
import in.testuniversity.entity.Stream;
import in.testuniversity.entity.Topic;
import in.testuniversity.exception.StreamNotFoundException;
import in.testuniversity.exception.TopicAlreadyExistsException;
import in.testuniversity.exception.TopicNotFoundException;
import in.testuniversity.mapper.TopicMapper;
import in.testuniversity.repository.StreamRepository;
import in.testuniversity.repository.TopicRepository;
import in.testuniversity.service.TopicService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class TopicServiceImpl implements TopicService {
	
	private final StreamRepository streamRepository;
	private final TopicRepository topicRepository;
	private final TopicMapper topicMapper;
	
	public TopicServiceImpl(StreamRepository streamRepository, TopicRepository topicRepository, TopicMapper topicMapper) {
		this.streamRepository = streamRepository;
		this.topicRepository = topicRepository;
		this.topicMapper = topicMapper;
	}
	
	@Override
	public TopicDTO createTopic(TopicDTO topicDTO) throws TopicAlreadyExistsException{
		if(topicDTO == null || topicDTO.getTopicName() == null || topicDTO.getStreamId() == null) {
			throw new IllegalArgumentException("Topic DTO, name or Stream ID cannot be null.");
		}
		//Checking if Stream exists, in which we are creating the topic
		Stream stream = streamRepository.findById(topicDTO.getStreamId())
				.orElseThrow(()-> new StreamNotFoundException("Stream not found, in which you are trying to create the Topic"));
		
		//Checking if the Topic already exists
		if(topicRepository.existsByTopicNameAndStreamId(topicDTO.getTopicName(), topicDTO.getStreamId())) {
			throw new TopicAlreadyExistsException("Topic with name "+topicDTO.getTopicName()+" already exists under the specified Stream.");
		}
		Topic topic = topicMapper.dtoToEntity(topicDTO);
		topic.setStream(stream);
		Topic savedTopic = topicRepository.save(topic);
		return topicMapper.entityToDTO(savedTopic);
	}

	@Override
	public Page<TopicDTO> getAllTopicsByStream(Long streamId, Pageable pageable) {
		if(!streamRepository.existsById(streamId)) {
			throw new StreamNotFoundException("Stream not found with id:"+streamId);
		}
		Page<Topic> topicsPage = topicRepository.findByStreamId(streamId, pageable);
		return topicsPage
				.map(topicMapper::entityToDTO);
	}
	
	@Override
	public TopicDTO getTopicById(Long topicId) {
		return topicRepository.findById(topicId) 
				.map(topicMapper::entityToDTO)
				.orElseThrow(()-> new TopicNotFoundException("Topic is not present with id:"+topicId));
	}
	
	@Override
	public TopicDTO updateTopic(Long topicId, TopicDTO topicDTO) {
		Topic topic = topicRepository.findById(topicId)
				.orElseThrow(()-> new TopicNotFoundException("Topic with topic id: "+topicId+" not found"));
		
		topic.setTopicName(topicDTO.getTopicName());
		Topic updatedTopic = topicRepository.save(topic);
		return topicMapper.entityToDTO(updatedTopic);
	}


	@Override
	public boolean deleteTopic(Long topicId) {
		if(!topicRepository.existsById(topicId)) {
			throw new TopicNotFoundException("Topic with id:"+topicId+" doesn't exist for deletion");
		}
		topicRepository.deleteById(topicId);
		return true;
	}

}
