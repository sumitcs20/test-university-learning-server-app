package in.testuniversity.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;

@Tag(name = "Authentication", description = "API for user authentication")
@RestController
@RequestMapping("/api/login")
public class LoginUserController {

	private final UserDetailsService userDetailsService;
	private final JwtService jwtService;
	private final AuthenticationManager authenticationManager;
	
	public LoginUserController(UserDetailsService userDetailsService, JwtService jwtService, AuthenticationManager authenticationManager) {
		this.userDetailsService = userDetailsService;
		this.jwtService = jwtService;
		this.authenticationManager = authenticationManager;
	}
	
	@Operation(summary = "Login User", description = "Authenticate user and generate JWT token",
			responses = {
					@ApiResponse(responseCode = "200", description = "Successfully authenticated"),
					@ApiResponse(responseCode = "401", description = "Invalid Credentials")
			})
	@PostMapping
	public ResponseEntity<AuthResponseDTO> loginUser(@RequestBody LoginUserDTO loginUserDTO) throws UserNotFoundException{
		
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginUserDTO.getEmailOrMobile(), loginUserDTO.getPassword()));
			
			// Load UserDetails(from your custom UserDetailsService implementation) & only executes if the user is valid and authenticated
			UserDetails userDetails = userDetailsService.loadUserByUsername(loginUserDTO.getEmailOrMobile());
			String jwtToken = jwtService.generateToken(userDetails); // calling generate token method of JwtService class for generating token
			return ResponseEntity.ok(new AuthResponseDTO(jwtToken, loginUserDTO));

		}catch(BadCredentialsException e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new AuthResponseDTO("Invalid Credentials", loginUserDTO));
		}
	}
}
