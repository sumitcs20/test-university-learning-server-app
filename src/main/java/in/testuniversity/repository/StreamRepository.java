package in.testuniversity.repository;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.testuniversity.entity.Stream;

@Repository
public interface StreamRepository extends CrudRepository<Stream, Long> {
	
	// Method to fetch a Stream by its name (useful for admin operations)
	Optional<Stream> findByStreamName(String streamName);
    
    // Check if a Stream exists by its name
    boolean existsByStreamName(String streamName);
}
