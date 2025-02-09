package in.testuniversity.service;

import java.util.List;

import in.testuniversity.dto.StreamDTO;
import in.testuniversity.exception.StreamAlreadyExistsException;

public interface StreamService {
	
	//To create a new Stream
	StreamDTO createStream(StreamDTO streamDTO) throws StreamAlreadyExistsException;
	
	//To fetch a stream based on stream Id
	StreamDTO getStreamById(Long streamId);

	//To fetch all the streams available
	List<StreamDTO> getAllStreams();
	
	//To update a stream based on stream id
	StreamDTO updateStream(Long id, StreamDTO streamDTO);
	
	//To delete a stream based on stream id passed	
	boolean deleteStream(Long streamId);

}
