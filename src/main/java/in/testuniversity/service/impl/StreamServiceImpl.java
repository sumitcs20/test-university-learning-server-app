package in.testuniversity.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import in.testuniversity.dto.StreamDTO;
import in.testuniversity.entity.Stream;
import in.testuniversity.exception.StreamAlreadyExistsException;
import in.testuniversity.exception.StreamNotFoundException;
import in.testuniversity.mapper.StreamMapper;
import in.testuniversity.repository.StreamRepository;
import in.testuniversity.service.StreamService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class StreamServiceImpl implements StreamService {

	private final StreamRepository streamRepository;
	private final StreamMapper streamMapper;
	
	public StreamServiceImpl(StreamRepository streamRepository, StreamMapper streamMapper) {
		this.streamRepository = streamRepository;
		this.streamMapper = streamMapper;
	}
	@Override
	public StreamDTO createStream(StreamDTO streamDTO) throws StreamAlreadyExistsException{
		if(streamDTO == null || streamDTO.getStreamName() == null || streamDTO.getStreamName().isEmpty()) {
			throw new IllegalArgumentException("StreamDTO or Stream name cannot be null or empty.");
		}
		if(streamRepository.existsByStreamName(streamDTO.getStreamName())) {
			throw new StreamAlreadyExistsException("Can't add this stream, the Stream ia already present ");
		}
		Stream stream = streamMapper.dtoToEntity(streamDTO);
		stream = streamRepository.save(stream);
		return streamMapper.entityToDTO(stream);
	}

	@Override
	public StreamDTO getStreamById(Long streamId) {
		return streamRepository.findById(streamId)
				.map(streamMapper::entityToDTO)
				.orElseThrow(()-> new StreamNotFoundException("Stream not found with Id:"+streamId));
	}

	@Override
	public List<StreamDTO> getAllStreams() {
		List<Stream> streamEntity = (List<Stream>) streamRepository.findAll();
		return streamEntity.stream()
				.map(streamMapper::entityToDTO)
				.collect(Collectors.toList());
	}

	@Override
	public StreamDTO updateStream(Long id, StreamDTO streamDTO) {
		//Fetching existing stream from database
		Stream stream = streamRepository.findById(id)
				.orElseThrow(()-> new StreamNotFoundException("Stream not found with id: "+id));
		
		stream.setStreamName(streamDTO.getStreamName());
		Stream updatedStream = streamRepository.save(stream);
		return streamMapper.entityToDTO(updatedStream);
	}
	
	@Override
	public boolean deleteStream(Long streamId) {
		if(!streamRepository.existsById(streamId)) {
			throw new StreamNotFoundException("Stream not found with id:"+streamId);
		}
		streamRepository.deleteById(streamId);
		return true;
	}

}
