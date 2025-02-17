package in.testuniversity.service.impl;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import in.testuniversity.dto.UserDTO;
import in.testuniversity.entity.Role;
import in.testuniversity.entity.User;
import in.testuniversity.exception.UserAlreadyExistsException;
import in.testuniversity.exception.UserNotFoundException;
import in.testuniversity.mapper.UserMapper;
import in.testuniversity.repository.UserRepository;
import in.testuniversity.service.RoleService;
import in.testuniversity.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService{

	private final UserRepository userRepository;
	private final RoleService roleService;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;
	
	public UserServiceImpl(UserRepository userRepository, RoleService roleService, UserMapper userMapper, PasswordEncoder passwordEncoder) {
			this.userRepository = userRepository;
			this.roleService = roleService;
			this.userMapper = userMapper;
			this.passwordEncoder = passwordEncoder;
		}	
		public UserDTO registerUser(UserDTO userDTO) throws UserAlreadyExistsException {
			String emailOrMobile = userDTO.getEmailOrMobile();
			
			if(isValidEmail(emailOrMobile) && userRepository.findByEmailId(emailOrMobile).isPresent()) {
				throw new UserAlreadyExistsException("User already exists with the emailId: "+emailOrMobile);
			}else if(isValidMobile(emailOrMobile) && userRepository.findByMobile(emailOrMobile).isPresent()) {
				throw new UserAlreadyExistsException("User already exists with the mobile number: "+emailOrMobile);
			}
			//Encoding password and setting to userDTO, which will be passed to mapper for mapping
			String encodedPassword = passwordEncoder.encode(userDTO.getPassword());
			userDTO.setPassword(encodedPassword);
			
			User user = userMapper.dtoToEntity(userDTO);
			// Set default role as "USER" by fetching it via RoleService
			Role defaultRole = roleService.findByRole("USER");
	        user.setRole(defaultRole);
			
			User savedUser = userRepository.save(user);
			return userMapper.entityToDTO(savedUser);
		}
		
	    @Override
	    public UserDTO getUserByEmailOrMobile(String emailOrMobile) {
	        User user = userRepository.findByEmailIdOrMobile(emailOrMobile, emailOrMobile)
	                .orElseThrow(() -> new UserNotFoundException("User not found"));
	        return userMapper.entityToDTO(user);
	    }
		
		// Helper method to validate email // Use a regex or custom email validator
	    private boolean isValidEmail(String email) {
	        return email != null && email.matches("^[A-Za-z0-9+_.-]+@(.+)$");
	    }

	    // Helper method to validate phone number // Simple validation
	    private boolean isValidMobile(String mobile) {
	        return mobile != null && mobile.matches("^[0-9]{10}$");
	    }
}