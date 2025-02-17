package in.testuniversity.dto;

public class AuthResponseDTO {

	private String token;
	private LoginUserDTO loginUserDTO;
	
	public AuthResponseDTO(String token, LoginUserDTO loginUserDTO) {
		this.token = token;
		this.loginUserDTO = loginUserDTO;
	}
	
    public String getToken() {
        return token;
    }

    public LoginUserDTO getLoinUserDTO() {
        return loginUserDTO;
    }
	
}
