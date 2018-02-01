package pl.coderslab;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
public class BootDemoTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(BootDemoTestApplication.class, args);
	}
}
