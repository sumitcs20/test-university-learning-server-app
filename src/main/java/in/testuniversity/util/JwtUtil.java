package in.testuniversity.util;

import java.util.Date;
import java.util.function.Function;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import in.testuniversity.exception.JwtParseException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtUtil {

	private SecretKey key;
	
	// Convert the secret key string to a proper SecretKey object
	public JwtUtil(@Value("${jwt.secret}") String secretKey) {
		byte[] keyBytes =Decoders.BASE64.decode(secretKey);
		this.key = Keys.hmacShaKeyFor(keyBytes);
	}
	
	//Method for generating token
	public String generateToken(String userName, long expirationTime) {
		return Jwts.builder()
				.setSubject(userName)
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + expirationTime))
				.signWith(key)
				.compact();
	}
	

	//Method to take String token and and a Claims method to call apply method of Claim, the token is passed to another method to get all the claims
	private Claims extractAllClaims(String token) {
		try {
			return Jwts.parserBuilder()
					.setSigningKey(key)
					.build()
					.parseClaimsJws(token)
					.getBody();
		}catch(JwtException ex) {
			throw new JwtParseException("Invalid JWT Token! "+ex.getMessage(), ex);
		}
	}
	
	//Generic method for claims extraction apply method
	public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	
	// Extract userName from the token
	public String extractUserName(String token) {
		return extractClaim(token, Claims::getSubject);
	}
	
	// Checking is token expired
    public boolean isTokenExpired(String token) {
        return extractClaim(token, Claims::getExpiration).before(new Date());
    }
    
    // Validate token
    public boolean validateToken(String token, String userName) {
        return extractUserName(token).equals(userName) && !isTokenExpired(token);
    }
	

		
}
