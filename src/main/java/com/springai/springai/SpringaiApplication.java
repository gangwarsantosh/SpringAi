package com.springai.springai;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.http.client.HttpClientAutoConfiguration;
import org.springframework.boot.autoconfigure.web.client.RestClientAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Spring Boot Application - English Teaching AI Assistant
 * 
 * Features:
 * - Chat conversations with AI (OpenAI / Ollama)
 * - Grammar correction analysis
 * - Pronunciation feedback
 * - Vocabulary management
 * - Speaking practice scenarios
 * - User analytics and progress tracking
 * - Achievement system
 * 
 * API: 23 endpoints across 7 resource groups
 * Database: H2 (development) / MySQL (production)
 * AI Models: OpenAI GPT-4 (primary) / Ollama (fallback)
 */
@SpringBootApplication(exclude = { HttpClientAutoConfiguration.class, RestClientAutoConfiguration.class })
public class SpringaiApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringaiApplication.class, args);
		System.out.println("\n" +
			"╔════════════════════════════════════════════════════════════════╗\n" +
			"║   English Teaching AI Assistant - Spring Boot Backend         ║\n" +
			"║   ✓ Chat Endpoints (2)                                        ║\n" +
			"║   ✓ User Endpoints (2)                                        ║\n" +
			"║   ✓ Analytics Endpoints (3)                                   ║\n" +
			"║   ✓ Vocabulary Endpoints (4)                                  ║\n" +
			"║   ✓ Speaking Practice Endpoints (4)                           ║\n" +
			"║   ✓ Grammar Endpoints (2)                                     ║\n" +
			"║   ✓ Achievement Endpoints (1)                                 ║\n" +
			"║   Total: 18 Endpoints Ready                                   ║\n" +
			"║   Server: http://localhost:8080                               ║\n" +
			"║   Docs: See SETUP_GUIDE.md & API_REFERENCE.md                ║\n" +
			"╚════════════════════════════════════════════════════════════════╝\n"
		);
	}

	/**
	 * Configure CORS for frontend access
	 */
	@Bean
	public WebMvcConfigurer corsConfigurer() {
		return new WebMvcConfigurer() {
			@Override
			public void addCorsMappings(CorsRegistry registry) {
				registry.addMapping("/api/**")
					.allowedOrigins("*")
					.allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
					.allowedHeaders("*")
					.maxAge(3600);
			}
		};
	}
}
