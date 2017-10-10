package co.gauth.sample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

@SpringBootApplication
public class GauthSampleApplication extends WebMvcConfigurerAdapter {
	public static void main(String[] args) {
		SpringApplication.run(GauthSampleApplication.class, args);
	}
}
