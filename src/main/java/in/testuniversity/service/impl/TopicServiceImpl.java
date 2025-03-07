package in.testuniversity.service.impl;

import java.util.List;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
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
	@Cacheable(value = "topicsCache", key = "#streamId + '-' + #page + '-' + #size + '-' + #sortBy + '-' + #sortDir")
	public Page<TopicDTO> getAllTopicsByStream(Long streamId, int page, int size, String sortBy, String sortDir) {
		if(!streamRepository.existsById(streamId)) {
			throw new StreamNotFoundException("Stream not found with id:"+streamId);
		}
		
		// Validate sorting field (Security Measure)
        List<String> allowedSortFields = List.of("topicName", "id");
        if (!allowedSortFields.contains(sortBy)) {
            throw new IllegalArgumentException("Invalid sort field: " + sortBy);
        }
		
		//validating the sortDir received for direction(ascending or descending)
		Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		
		//Page based on page#, content size on page and direction of sorting
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
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
	@CachePut(value = "topicsCache", key = "topicId")
	public TopicDTO updateTopic(Long topicId, TopicDTO topicDTO) {
		Topic topic = topicRepository.findById(topicId)
				.orElseThrow(()-> new TopicNotFoundException("Topic with topic id: "+topicId+" not found"));
		
		topic.setTopicName(topicDTO.getTopicName());
		Topic updatedTopic = topicRepository.save(topic);
		return topicMapper.entityToDTO(updatedTopic);
	}


	@Override
	@CacheEvict(value = "topicsCache", key = "#streamId")
	public boolean deleteTopic(Long topicId) {
		if(!topicRepository.existsById(topicId)) {
			throw new TopicNotFoundException("Topic with id:"+topicId+" doesn't exist for deletion");
		}
		topicRepository.deleteById(topicId);
		return true;
	}

}
