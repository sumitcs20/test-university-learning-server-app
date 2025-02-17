package in.testuniversity.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import in.testuniversity.util.JwtUtil;
import jakarta.transaction.Transactional;

@Service
@Transactional
public class JwtService {
	
	private final JwtUtil jwtUtil;
	private final long expirationTime;
	
	public JwtService(JwtUtil jwtUtil, @Value("${jwt.expiration}") long expirationTime) {
		this.jwtUtil = jwtUtil;
		this.expirationTime = expirationTime;
	}
	 /**
     * Generate a JWT token for a given user.
     * @param user - The authenticated user.
     * @return The generated JWT token.
     */
	public String generateToken(UserDetails userDetails) {
		return jwtUtil.generateToken(userDetails.getUsername(), expirationTime);
	}
	/***
	 * Extract the email or mobile from JWT token
	 * @param token - the JWT token(String)
	 * @return extracted email or mobile
	 */
	public String extractEmailOrMobile(String token) {
		return jwtUtil.extractUserName(token);
	}
	
	/****
	 * Validate is token valid
	 * @param token - The JWT token(String)
	 * @param user - The authenticated user
	 * @return true if valid otherwise false
	 */
	public boolean isValidToken(String token, UserDetails userDetails) {
		return jwtUtil.validateToken(token, userDetails.getUsername());
	}
	
	/****
	 * Validate is token valid
	 * @param token - The JWT token(String)
	 * @return true if expired otherwise false
	 */
	public boolean isTokenExpired(String token) {
		return jwtUtil.isTokenExpired(token);
	}
}
