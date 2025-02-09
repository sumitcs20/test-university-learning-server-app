package in.testuniversity.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import in.testuniversity.dto.OptionDTO;
import in.testuniversity.entity.Option;
import in.testuniversity.exception.OptionAlreadyExistsException;
import in.testuniversity.exception.OptionNotFoundException;
import in.testuniversity.exception.QuestionNotFoundException;
import in.testuniversity.mapper.OptionMapper;
import in.testuniversity.repository.OptionRepository;
import in.testuniversity.repository.QuestionRepository;
import in.testuniversity.service.OptionService;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class OptionServiceImpl implements OptionService {
	
	private final OptionRepository optionRepository;
	private final QuestionRepository questionRepository;
	private final OptionMapper optionMapper;
	
	public OptionServiceImpl(OptionRepository optionRepository, QuestionRepository questionRepository, OptionMapper optionMapper) {
		this.optionRepository = optionRepository;
		this.questionRepository = questionRepository;
		this.optionMapper = optionMapper;
	}

	@Override
	public OptionDTO createOption(OptionDTO optionDTO) {
		if(optionDTO == null || optionDTO.getOptionText() == null || optionDTO.getQuestionId() == null) {
			throw new IllegalArgumentException("Option DTO, Option text or question Id can't be null");
		}
		//Validate the Question existence for the option
		if(!questionRepository.existsById(optionDTO.getQuestionId())) {
			throw new QuestionNotFoundException("Question not found with Id: "+optionDTO.getQuestionId());
		}
		  // Validate duplicate Option
	    if (optionRepository.existsByOptionTextAndQuestionId(optionDTO.getOptionText(), optionDTO.getQuestionId())) {
	        throw new OptionAlreadyExistsException("The option with the text '" + optionDTO.getOptionText() + "' already exists for question ID: " + optionDTO.getQuestionId());
	    }
	    //Validate if any option is marked as correct for the same question, un mark any existing correct option for the same
	    if(optionDTO.getIsCorrect()) {
	    	optionRepository.findByQuestionIdAndIsCorrectTrue(optionDTO.getQuestionId())
	    		.ifPresent((correctOption)->{
	    			correctOption.setIsCorrect(false);
	    			optionRepository.save(correctOption);
	    		});
	    }
	    
	    
		Option option = optionMapper.dtoToEntity(optionDTO);		
		Option savedOption = optionRepository.save(option);
		return optionMapper.entityToDTO(savedOption);
	}
	
	//Implementation of getAllOptions method of service class
	@Override
	public List<OptionDTO> getAllOptionsByQuestion(Long questionId) {
		if(!questionRepository.existsById(questionId)) {
			throw new QuestionNotFoundException("Question not found with question Id: "+questionId);
		}
		List<Option> options = optionRepository.findByQuestionId(questionId);
		return options.stream()
				.map(optionMapper::entityToDTO)
				.collect(Collectors.toList());
	}

	//Implementation of getOptionById method of service class
	@Override
	public OptionDTO getOptionById(Long optionId) {
		return optionRepository.findById(optionId)
				.map(optionMapper::entityToDTO)
				.orElseThrow(()-> new OptionNotFoundException("Option for the question not found with id: "+optionId));
	}
	
	////Implementation of updateOption method of service class
	@Override
	public OptionDTO updateOption(Long id, OptionDTO optionDTO) {
		return optionRepository.findById(id)
				.map(option->{
					option.setOptionText(optionDTO.getOptionText());
					Option updatedOption = optionRepository.save(option);
					return optionMapper.entityToDTO(updatedOption);
				})
				.orElseThrow(()->{
					return new OptionNotFoundException("Option not found with id:"+id);
				});
	}

	//Implementation of deleteOption method of service class
	@Override
	public boolean deleteOption(Long optionId) {
		if(!optionRepository.existsById(optionId)) {
			throw new OptionNotFoundException("Option not found with option Id: "+optionId);
		}
		optionRepository.deleteById(optionId);
		return true;
	}

	//To mark an option as correct (implemented method of Service class interface)
	@Override
	public OptionDTO markAsCorrectOption(Long optionId) {
		Option option = optionRepository.findById(optionId)
				.orElseThrow(()-> new OptionNotFoundException("Option not found with Id: {}"+optionId));
		//To un mark any existing correct option for the same question
		optionRepository.findByQuestionIdAndIsCorrectTrue(option.getQuestion().getId())
				.ifPresent((correctOption)->{
					correctOption.setIsCorrect(false);
					optionRepository.save(correctOption);
				});
		option.setIsCorrect(true);
		Option updatedOption = optionRepository.save(option);
		return optionMapper.entityToDTO(updatedOption);
	}

	//To fetch correct option for a question
	@Override
	public OptionDTO getCorrectOptionByQuestion(Long questionId) {
		Option option = optionRepository.findByQuestionIdAndIsCorrectTrue(questionId)
				.orElseThrow(()-> new OptionNotFoundException("No correct option found for Question ID: " + questionId));
		return optionMapper.entityToDTO(option);
	}

}
