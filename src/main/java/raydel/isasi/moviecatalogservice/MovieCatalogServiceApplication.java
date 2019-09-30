package raydel.isasi.moviecatalogservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

@SpringBootApplication
public class MovieCatalogServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(MovieCatalogServiceApplication.class, args);
	}
	
	@Bean
	public RestTemplate resttemplate() {
		
		return new RestTemplate();
	}
	
	@Bean
	public WebClient.Builder webclientbuilder() {
		
		return  WebClient.builder();
	}

}