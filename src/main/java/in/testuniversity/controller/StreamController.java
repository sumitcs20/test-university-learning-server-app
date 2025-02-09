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

import in.testuniversity.dto.StreamDTO;
import in.testuniversity.service.StreamService;

@RestController
@RequestMapping("/api/streams")
public class StreamController {

	private final StreamService streamService;
	
	public StreamController(StreamService streamService) {
		this.streamService = streamService;
	}
	
	@PostMapping
	public ResponseEntity<StreamDTO> createStream(@RequestBody StreamDTO streamDTO){
		StreamDTO createdStream = streamService.createStream(streamDTO);
		return new ResponseEntity<>(createdStream, HttpStatus.CREATED);
	}
	
	@GetMapping("/{id}")
	public ResponseEntity<StreamDTO> getStreamById(@PathVariable Long id){
		StreamDTO stream = streamService.getStreamById(id);
		return ResponseEntity.ok(stream);
	}
	
	@GetMapping
	public ResponseEntity<List<StreamDTO>> getAllStreams(){
		List<StreamDTO> streams = streamService.getAllStreams();
		return ResponseEntity.ok(streams);
	}
	
	@PutMapping("/{id}")
    public ResponseEntity<StreamDTO> updateStream(@PathVariable Long id, @RequestBody StreamDTO streamDTO) {
        StreamDTO updatedStream = streamService.updateStream(id, streamDTO);
        return ResponseEntity.ok(updatedStream);
    }
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStreamById(@PathVariable Long id){
		streamService.deleteStream(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
}
