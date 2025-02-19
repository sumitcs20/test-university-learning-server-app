package in.testuniversity.controller;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import in.testuniversity.dto.UserDTO;
import in.testuniversity.exception.UserAlreadyExistsException;

import in.testuniversity.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@SecurityRequirement(name = "Bearer Authentication")  // ✅ Add this at class level
@Tag(name = "User Management", description = "APIs for managing users")
@RestController
@RequestMapping("/api/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@Operation(summary = "Register User", description = "APi to register a user in the application",
			responses = {
					@ApiResponse(responseCode = "201", description = "User registered sucessfully"),
					@ApiResponse(responseCode = "409", description = "User already exists")
			})
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) throws UserAlreadyExistsException{
		UserDTO createduser = userService.registerUser(userDTO);
		return new ResponseEntity<>(createduser, HttpStatus.CREATED);
	}
	
	@Operation(summary = "Get User", description = "APi to get the user with email or mobile")
	@GetMapping("/{emailOrMobile}")
	public ResponseEntity<Optional<UserDTO>> getUserByEmailOrMobile(@PathVariable String emailOrMobile){
		return null;
	}

}
