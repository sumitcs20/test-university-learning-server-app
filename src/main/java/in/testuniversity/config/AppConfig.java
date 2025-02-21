package in.testuniversity.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
public class AppConfig {

	@Bean
	@Profile("dev")
	public String devBean() {
		return "Development profile active";
	}
	
	@Bean
	@Profile("test")
	public String testBean() {
		return "Test profile active";
	}
	
	@Bean
	@Profile("prod")
	public String prodBean() {
		return "Production profile active";
	}
}
