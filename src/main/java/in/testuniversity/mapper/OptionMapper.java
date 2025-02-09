package in.testuniversity.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import in.testuniversity.dto.OptionDTO;
import in.testuniversity.entity.Option;
import in.testuniversity.entity.Question;
import in.testuniversity.exception.MappingException;

@Component
public class OptionMapper implements BaseMapper<Option, OptionDTO> {
	
	private static final Logger LOGGER = LogManager.getLogger(OptionMapper.class);

	@Override
	public Option dtoToEntity(OptionDTO optionDTO) throws MappingException {
		if(optionDTO == null) {
			LOGGER.error("Mapping failed: option DTO is null.");
			throw new MappingException("Input option DTO can't be null for mapping to option entity");
		}
		Option option = new Option();
		option.setOptionText(optionDTO.getOptionText());
		
		if(optionDTO.getQuestionId() != null) {
			Question question = new Question();
			question.setId(optionDTO.getQuestionId());
			option.setQuestion(question);
		}else {
			LOGGER.error("Mapping failed: Question ID in OptionDTO is null");
			throw new MappingException("Question ID in OptionDTO cannot be null.");
		}
		return option;
	}

	@Override
	public OptionDTO entityToDTO(Option option) throws MappingException {
		if(option == null) {
			LOGGER.error("Mapping failed: option entity is null.");
			throw new MappingException("Input option entity can't be null for mapping to option DTO");
		}
		OptionDTO optionDTO = new OptionDTO();
		optionDTO.setOptionText(option.getOptionText());
		
		if(option.getQuestion() != null && option.getQuestion().getId() != null) {
			optionDTO.setQuestionId(option.getQuestion().getId());
		}else {
			LOGGER.error("Mapping failed: Question ID in Option entity is null");
			throw new MappingException("Question ID in Option entity cannot be null");
		}
		
		return optionDTO;
	}

}
