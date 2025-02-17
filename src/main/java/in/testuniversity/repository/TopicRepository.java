package in.testuniversity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.testuniversity.entity.Topic;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {

    // Find all topics under a specific Stream
    Page<Topic> findByStreamId(Long streamId, Pageable pageable);
    
    // Check if a Topic exists by its name under a specific Stream
    boolean existsByTopicNameAndStreamId(String topicName, Long streamId);
}
