package in.testuniversity.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import in.testuniversity.dto.QuestionDTO;
import in.testuniversity.entity.Option;
import in.testuniversity.entity.Question;
import in.testuniversity.exception.OptionNotFoundException;
import in.testuniversity.exception.QuestionAlreadyExistsException;
import in.testuniversity.exception.QuestionNotFoundException;
import in.testuniversity.exception.TopicNotFoundException;
import in.testuniversity.mapper.OptionMapper;
import in.testuniversity.mapper.QuestionMapper;
import in.testuniversity.repository.OptionRepository;
import in.testuniversity.repository.QuestionRepository;
import in.testuniversity.repository.TopicRepository; 
import in.testuniversity.service.QuestionService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class QuestionServiceImpl implements QuestionService{

	private final QuestionRepository questionRepository;
	private final TopicRepository topicRepository;
	private final QuestionMapper questionMapper;
	private final OptionRepository optionRepository;
	private final OptionMapper optionMapper;

	public QuestionServiceImpl(QuestionRepository questionRepository, TopicRepository topicRepository, QuestionMapper questionMapper,OptionRepository optionRepository ,OptionMapper optionMapper) {
		this.questionRepository = questionRepository;
		this.topicRepository = topicRepository;
		this.questionMapper = questionMapper;
		this.optionRepository = optionRepository;
		this.optionMapper = optionMapper;
	}

	@Override //Question service method to create a new question
	public QuestionDTO createQuestionWithOptions(QuestionDTO questionDTO) {
		if(questionDTO == null || questionDTO.getQuestionText() == null || questionDTO.getTopicId() == null) {
			throw new IllegalArgumentException("Question DTO, question text, or topic ID cannot be null.");
		}
		//Validating topic existence
		if(!topicRepository.existsById(questionDTO.getTopicId())){
			throw new TopicNotFoundException("The Topic not found in which you are trying to create a question");
		}
		
		//Checking whether the question already exist in the topic..
		if(questionRepository.existsByQuestionTextAndTopicId(questionDTO.getQuestionText(), questionDTO.getTopicId())) {
			throw new QuestionAlreadyExistsException("The question you are trying to add already exists");
		}
		//Map the DTO to entity, save and map back to DTO
		Question question = questionMapper.dtoToEntity(questionDTO);
		Question savedQuestion = questionRepository.save(question);

		List<Option> options = new ArrayList<>();
		//Handling the Options of the methods
		if(questionDTO.getOptions() != null && !questionDTO.getOptions().isEmpty()) {
			options = questionDTO.getOptions().stream()
					.map(optionDTO->{
						Option mappedOption = optionMapper.dtoToEntity(optionDTO);
						mappedOption.setQuestion(savedQuestion);
						return mappedOption;
					}).collect(Collectors.toList());
			optionRepository.saveAll(options);
		}
		
		//Marking the correct option
		if(questionDTO.getCorrectOptionId()!= null) {
			Option correctOption = options.stream()
					.filter(option-> option.getId().equals(questionDTO.getCorrectOptionId()))
					.findFirst()
					.orElseThrow(()-> new OptionNotFoundException("Correct option not found for question ID: " + savedQuestion.getId()));
			correctOption.setIsCorrect(true);
			optionRepository.save(correctOption);
		}
		
		return questionMapper.entityToDTO(savedQuestion);
		
	}
	
	@Override
	@Cacheable(value = "questionsCache", key = "#topicId + '-' + #page + '-' + #size + '-' + #sortBy + '-' + #sortDir")
	public Page<QuestionDTO> getAllQuestionsByTopic(Long topicId, int page, int size, String sortBy, String sortDir) {
		if(!topicRepository.existsById(topicId)) {
			throw new TopicNotFoundException("Topic not found with id: "+topicId);
		}
		
		// Sorting logic: Ascending or Descending
		Sort.Direction direction = sortDir.equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
		
		// Validate sorting field to prevent security issues
	    List<String> allowedSortFields = List.of("questionText", "id");
	    if (!allowedSortFields.contains(sortBy)) {
	        throw new IllegalArgumentException("Invalid sort field: " + sortBy);
	    }
		
		// Create a Pageable object with page number, size, and sorting
		Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sortBy));
		
		Page<Question> questions = questionRepository.findByTopicId(topicId, pageable); //Fetching questions from db
		return questions
				.map(questionMapper::entityToDTO);
	}

	@Override
	public QuestionDTO getQuestionById(Long questionId) {
		return questionRepository.findById(questionId)
				.map(questionMapper::entityToDTO)
				.orElseThrow(()-> new QuestionNotFoundException("Question not found with id: "+questionId));
	}

	@Override
	@CachePut(value = "questionsCache", key = "questionId")
	public QuestionDTO updateQuestion(Long questionId, QuestionDTO questionDTO) {
		Question question = questionRepository.findById(questionId)
				.orElseThrow(()-> new QuestionNotFoundException("Question not found with id: "+questionId));
		
		question.setQuestionText(questionDTO.getQuestionText());
		Question updatedQuestion = questionRepository.save(question);
		return questionMapper.entityToDTO(updatedQuestion);
	}
	

	@Override
	@CacheEvict(value = "questionsCache", key = "#topicId")
	public boolean deleteQuestion(Long questionId) {
		if(!questionRepository.existsById(questionId)) {
			throw new QuestionNotFoundException("The question not found which you want to delete: "+questionId);
		}
		questionRepository.deleteById(questionId);
		return true;
	}

}
