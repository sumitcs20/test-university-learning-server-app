package in.testuniversity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import in.testuniversity.security.JwtAuthenticationEntryPoint;
import in.testuniversity.security.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
	
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;
    private final UserDetailsService userDetailsService;
    
    public SecurityConfig(JwtAuthenticationFilter jwtAuthenticationFilter, JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint, UserDetailsService userDetailsService) {
    	this.jwtAuthenticationFilter = jwtAuthenticationFilter;
    	this.jwtAuthenticationEntryPoint = jwtAuthenticationEntryPoint;
    	this.userDetailsService = userDetailsService;
    }
    
    /**
     * Configures security rules, authentication, and session management.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception{
    	return http
		    		.csrf(AbstractHttpConfigurer::disable)
		    		.authorizeHttpRequests(auth -> auth
		    				.requestMatchers(
		    						"/api/users/register",
		    						"/api/login",
		    						"/public/**",
		    						"/swagger-ui/**",  //  Allow Swagger UI
		                            "/v3/api-docs/**"   //  Allow API Docs
		    				).permitAll() //Public end   point login permitted
		    				// Secure endpoints based on roles:
	                        .requestMatchers("/api/admin/**").hasRole("ADMIN")
	                        .requestMatchers("/api/instructor/**").hasAnyRole("INSTRUCTOR", "ADMIN")
	                        .anyRequest().authenticated() // All other endpoints require authentication
		    		)
		    		.exceptionHandling(ex -> ex
		    				.authenticationEntryPoint(jwtAuthenticationEntryPoint)
		    		)
		    		
		    		.sessionManagement(session -> session
		    				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
		    		)
		    		.addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
		    		.build();
    }
    
    /**
     * Configures authentication provider.
     */
    @Bean
    public AuthenticationManager authenticationManager() {
    	DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
    	authProvider.setUserDetailsService(userDetailsService);
    	authProvider.setPasswordEncoder(passwordEncoder());
    	return new ProviderManager(authProvider);
    }
    
    /**
     * Configures password encoding using BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
    
}
