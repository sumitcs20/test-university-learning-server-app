package in.testuniversity.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import in.testuniversity.entity.Topic;

@Repository
public interface TopicRepository extends CrudRepository<Topic, Long> {

    // Find all topics under a specific Stream
    List<Topic> findByStreamId(Long streamId);
    
    // Check if a Topic exists by its name under a specific Stream
    boolean existsByTopicNameAndStreamId(String topicName, Long streamId);
}
