package in.testuniversity.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import in.testuniversity.entity.Option;

public interface OptionRepository extends JpaRepository<Option, Long> {
	
    // Find all options for a specific Question
	List<Option> findByQuestionId(Long questionId);

	//Find options associated with question
	boolean existsByOptionTextAndQuestionId(String optionText, Long questionId);
	
	//
	Optional<Option> findByQuestionIdAndIsCorrectTrue(Long questionId);
}
