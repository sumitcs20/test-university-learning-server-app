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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Stream Management" , description = "APIs to manage Streams")
@RestController
@RequestMapping("/api/streams")
public class StreamController {

	private final StreamService streamService;
	
	public StreamController(StreamService streamService) {
		this.streamService = streamService;
	}
	
	@Operation(summary = "Create a new Stream", description = "Adds a new stream to the system",
			responses = {
					@ApiResponse(responseCode = "201", description = "Stream created successfully"),
					@ApiResponse(responseCode = "409", description = "Stream Already present")
			})
	
	@PostMapping
	public ResponseEntity<StreamDTO> createStream(@RequestBody StreamDTO streamDTO){
		StreamDTO createdStream = streamService.createStream(streamDTO);
		return new ResponseEntity<>(createdStream, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Get stream by Id", description = "Fetches a stream by stream id",
			responses = {
					@ApiResponse(responseCode = "200", description = "Stream fetched successfully"),
					@ApiResponse(responseCode = "404", description = "Stream not found")
			})
	@GetMapping("/{id}")
	public ResponseEntity<StreamDTO> getStreamById(@PathVariable Long id){
		StreamDTO stream = streamService.getStreamById(id);
		return ResponseEntity.ok(stream);
	}
	
	@Operation(summary = "Get all Streams", description = "Fetches all available streams",
			responses = {
					@ApiResponse(responseCode = "200", description = "List of streams fetched successfully")
			})
	@GetMapping
	public ResponseEntity<List<StreamDTO>> getAllStreams(){
		List<StreamDTO> streams = streamService.getAllStreams();
		return ResponseEntity.ok(streams);
	}
	
	@Operation(summary = "Update a stream", description = "Updates an existing stream",
			responses = {
			@ApiResponse(responseCode = "200", description = "Stream updated successfully"),
			@ApiResponse(responseCode = "404", description = "Stream not found")
			})
	@PutMapping("/{id}")
    public ResponseEntity<StreamDTO> updateStream(@PathVariable Long id, @RequestBody StreamDTO streamDTO) {
        StreamDTO updatedStream = streamService.updateStream(id, streamDTO);
        return ResponseEntity.ok(updatedStream);
    }
	
	@Operation(summary = "Delete Stream by Id", description = "Delete a stream with stream id")
	@ApiResponse(responseCode = "204", description = "Stream deleted successfully")
	@ApiResponse(responseCode = "404", description = "Stream not found")
	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteStreamById(@PathVariable Long id){
		streamService.deleteStream(id);
		return ResponseEntity.noContent().build();
	}
	
	
	
}
