package skillsrock.apiusers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class ApiusersApplication {

	public static void main(String[] args) {
		SpringApplication.run(ApiusersApplication.class, args);
	}

}
