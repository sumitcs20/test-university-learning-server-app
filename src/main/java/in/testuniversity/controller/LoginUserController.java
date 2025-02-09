package in.testuniversity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.testuniversity.dto.AuthResponseDTO;
import in.testuniversity.dto.LoginUserDTO;
import in.testuniversity.exception.UserNotFoundException;
import in.testuniversity.security.JwtService;

@RestController
@RequestMapping("/login")
public class LoginUserController {

	private UserDetailsService userDetailsService;
	private JwtService jwtService;
	private AuthenticationManager authenticationManager;
	
	public LoginUserController(UserDetailsService userDetailsService, JwtService jwtService, AuthenticationManager authenticationManager) {
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}
	
	@PostMapping
	public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody LoginUserDTO loginUserDTO) throws UserNotFoundException{
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getEmailOrMobile(), loginUserDTO.getPassword()));
			UserDetails userDetails = userDetailsService.loadUserByUsername(loginUserDTO.getEmailOrMobile());// Load UserDetails(from your custom UserDetailsService implementation)
			String jwtToken = jwtService.generateToken(userDetails); // calling generate token method of JwtService class for generating token
			return ResponseEntity.ok(new AuthResponseDTO(jwtToken, loginUserDTO));
		}catch(AuthenticationException ex) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials. Please try again.");
		}
	}
}
