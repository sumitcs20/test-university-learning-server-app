package in.testuniversity.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import in.testuniversity.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Find all questions under a specific Topic
    List<Question> findByTopicId(Long topicId);
    
    // Check if a Question exists by its text under a specific Topic
    boolean existsByQuestionTextAndTopicId(String questionText, Long topicId);
	
}
