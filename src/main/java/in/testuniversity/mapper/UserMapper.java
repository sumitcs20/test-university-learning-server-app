package in.testuniversity.mapper;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Component;

import in.testuniversity.dto.UserDTO;
import in.testuniversity.entity.User;
import in.testuniversity.exception.MappingException;

@Component
public class UserMapper implements BaseMapper<User, UserDTO>{
	
	private static final Logger LOGGER = LogManager.getLogger(UserMapper.class);

	@Override
	public User dtoToEntity(UserDTO userDTO) throws MappingException {
		if(userDTO == null) {
			LOGGER.error("Mapping failed: User DTO is null");
			throw new IllegalArgumentException("User DTO cannot be null");
		}
		User user = new User();
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		
		if (userDTO.getEmailOrMobile().contains("@")) {
            user.setEmailId(userDTO.getEmailOrMobile());
        } else {
            user.setMobile(userDTO.getEmailOrMobile());
        }
		
		user.setPassword(userDTO.getPassword());
		return user;
	}

	@Override
	public UserDTO entityToDTO(User user) throws MappingException {
		if(user == null) {
			LOGGER.error("Mapping failed: User entity is null");
			throw new IllegalArgumentException("User entity cannot be null");
		}
		UserDTO userDTO = new UserDTO();
		userDTO.setFirstName(user.getFirstName());
		userDTO.setLastName(user.getLastName());
		userDTO.setEmailOrMobile(user.getEmailId() != null ? user.getEmailId() : user.getMobile());
		return userDTO;
	}
}
