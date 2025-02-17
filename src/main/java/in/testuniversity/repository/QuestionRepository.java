package in.testuniversity.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import in.testuniversity.entity.Question;

public interface QuestionRepository extends JpaRepository<Question, Long> {

    // Find all questions under a specific Topic
    Page<Question> findByTopicId(Long topicId, Pageable pageable);
    
    // Check if a Question exists by its text under a specific Topic
    boolean existsByQuestionTextAndTopicId(String questionText, Long topicId);
	
}
