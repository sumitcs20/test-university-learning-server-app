package in.testuniversity.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import in.testuniversity.dto.QuestionDTO;
import in.testuniversity.entity.Question;
import in.testuniversity.entity.Topic;
import in.testuniversity.exception.MappingException;

@Component
public class QuestionMapper implements BaseMapper<Question, QuestionDTO> {

	private static final Logger LOGGER = LogManager.getLogger(QuestionMapper.class);
	
	@Override
	public Question dtoToEntity(QuestionDTO questionDTO) throws MappingException {
		if(questionDTO == null) {
			LOGGER.error("Mapping failed: input question DTO is null");
			throw new MappingException("Input Question DTO cannot be null during mapping to Question entity.");
		}
		Question question = new Question();
		question.setQuestionText(questionDTO.getQuestionText());
		
		if(questionDTO.getTopicId() != null) {
			Topic topic = new Topic();
			topic.setId(questionDTO.getTopicId());
			question.setTopic(topic);
		}else {
			LOGGER.error("Mapping failed: Topic Id in  question DTO is null");
			throw new MappingException("Topic Id in Question DTO cannot be null.");
		}
		return question;
	}

	@Override
	public QuestionDTO entityToDTO(Question question) throws MappingException {
		if(question == null) {
			LOGGER.error("Mapping failed: input question entity is null");
			throw new MappingException("Input Question entity cannot be null during mapping to Question DTO.");
		}
		QuestionDTO questionDTO = new QuestionDTO();
		questionDTO.setQuestionText(question.getQuestionText());
		
		if(question.getTopic() != null && question.getTopic().getId() != null) {
			questionDTO.setTopicId(question.getTopic().getId());
		}else {
			LOGGER.error("Mapping failed: Topic is null in question entity is null");
			throw new MappingException("Topic in Question entity cannot be null.");
		}
		
		return questionDTO;
	}

}
