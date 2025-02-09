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
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {
	
	private final UserService userService;
	
	public UserController(UserService userService) {
		this.userService = userService;
	}
	
	@PostMapping("/register")
	public ResponseEntity<UserDTO> registerUser(@Valid @RequestBody UserDTO userDTO) throws UserAlreadyExistsException{
		UserDTO createduser = userService.registerUser(userDTO);
		return new ResponseEntity<>(createduser, HttpStatus.CREATED);
	}
	
	
	@GetMapping("/{emailOrMobile}")
	public ResponseEntity<Optional<UserDTO>> getUserByEmailOrMobile(@PathVariable String emailOrMobile){
		return null;
	}

}
