package in.testuniversity.service;

import in.testuniversity.dto.UserDTO;
import in.testuniversity.exception.UserAlreadyExistsException;

public interface UserService {

	//For registering a user
	UserDTO registerUser(UserDTO userDTO) throws UserAlreadyExistsException; 

	UserDTO getUserByEmailOrMobile(String emailOrMobile);

}
