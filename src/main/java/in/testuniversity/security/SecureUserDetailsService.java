package in.testuniversity.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import in.testuniversity.entity.User;
import in.testuniversity.exception.UserNotFoundException;
import in.testuniversity.repository.UserRepository;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class SecureUserDetailsService implements UserDetailsService{
	
	private final UserRepository userRepository;
	
	public SecureUserDetailsService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}  
	
	@Override
	public UserDetails loadUserByUsername(String emailOrMobile) throws UsernameNotFoundException {
        // Fetch user from UserService
        User user = userRepository.findByEmailIdOrMobile(emailOrMobile, emailOrMobile)
        		.orElseThrow(()-> new UserNotFoundException("User not found with email or mobile: " + emailOrMobile));
    
        return new SecureUser(user);
    }
}
