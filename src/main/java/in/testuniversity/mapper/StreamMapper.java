package in.testuniversity.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import in.testuniversity.dto.StreamDTO;
import in.testuniversity.entity.Stream;
import in.testuniversity.exception.MappingException;

@Component
public class StreamMapper implements BaseMapper<Stream, StreamDTO>{

	private static final Logger LOGGER = LogManager.getLogger(StreamMapper.class);
	@Override
	public Stream dtoToEntity(StreamDTO streamDTO) throws MappingException {
		if(streamDTO == null) {
			LOGGER.error("Mapping failed: Input Stream DTO is null");
            throw new MappingException("Input Stream DTO cannot be null during mapping to Entity.");
		}
		Stream stream = new Stream();
		stream.setStreamName(streamDTO.getStreamName());
		
		return stream;
	}

	@Override
	public StreamDTO entityToDTO(Stream streamEntity) throws MappingException {
		if(streamEntity == null) {
			LOGGER.error("Mapping failed: Input Stream entity is null");
            throw new MappingException("Input Stream entity cannot be null during mapping to DTO.");
		}
		StreamDTO streamDTO = new StreamDTO();
		streamDTO.setStreamName(streamEntity.getStreamName());
		return streamDTO;
	}

}
