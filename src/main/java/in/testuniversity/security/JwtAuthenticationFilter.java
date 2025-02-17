package in.testuniversity.security;

import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter{

	private static final Logger LOGGER = LogManager.getLogger(JwtAuthenticationFilter.class);
	
	private final JwtService jwtService;
	private final UserDetailsService userDetailService;
	
	public JwtAuthenticationFilter(JwtService jwtService, UserDetailsService userDetailService) {
		this.jwtService = jwtService;
		this.userDetailService = userDetailService;
	}
	
	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) 
			throws ServletException, IOException {
		
		//Getting authorization header and extracting JWT
		String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
		
		if(authHeader == null || !authHeader.startsWith("Bearer ")) { 
			filterChain.doFilter(request, response);
			return;
		}
		String token = authHeader.substring(7); // Removes "Bearer " prefix from the token(Since, authHeader was not null and started with "Bearer ")
		try {
			String emailOrMobile = jwtService.extractEmailOrMobile(token);
			if(emailOrMobile != null && SecurityContextHolder.getContext().getAuthentication() == null) {
				//load user details from database..
				UserDetails userDetails = userDetailService.loadUserByUsername(emailOrMobile);
				
				if(jwtService.isValidToken(token, userDetails)) {
					UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
					authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request)); //Setting additional authentication detail
					
					SecurityContextHolder.getContext().setAuthentication(authentication);
					
					LOGGER.info("User: {} authenticated successfully! ", emailOrMobile);
				}
			}
		}catch(Exception e) {
			LOGGER.error("JWT Authentication failed: {} : {}", e.getMessage(), e);
		}
		
		filterChain.doFilter(request, response);
	}

}
