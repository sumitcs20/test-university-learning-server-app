package in.testuniversity.security;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	private static final Logger LOGGER = LogManager.getLogger(JwtAuthenticationEntryPoint.class);
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException ex) throws IOException, ServletException {
		
		LOGGER.error("Unauthorized access error: {}", ex.getMessage());
		response.setStatus(HttpServletResponse.SC_UNAUTHORIZED); //Setting response code to 401 Unauthorized
		response.setContentType("application/json");
		String jsonResponse = "{ \"error\": \"Unauthorized access! Please provide a valid token.\" }";
		response.getWriter().write(jsonResponse);		
	}

}
