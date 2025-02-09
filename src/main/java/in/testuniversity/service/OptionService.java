package in.testuniversity.service;

import java.util.List;
import in.testuniversity.dto.OptionDTO;

public interface OptionService {

	//To create a new option
	OptionDTO createOption(OptionDTO optionDTO);
	
	//To fetch all the options based on question id
	List<OptionDTO> getAllOptionsByQuestion(Long questionId);
		
	//To fetch option based on option Id
	OptionDTO getOptionById(Long optionId);
	
	//To update option based on option id
	OptionDTO updateOption(Long id, OptionDTO optionDTO);

	//To delete an option by option id
	boolean deleteOption(Long optionId);
	
	//To mark an option as correct
    OptionDTO markAsCorrectOption(Long optionId);

    //To get correct option for a question
    OptionDTO getCorrectOptionByQuestion(Long questionId);
	
}
