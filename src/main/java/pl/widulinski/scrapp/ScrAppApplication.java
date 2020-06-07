package pl.widulinski.scrapp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;



@SpringBootApplication
public class ScrAppApplication {


	public static void main(String[] args) {
	ConfigurableApplicationContext context = SpringApplication.run(ScrAppApplication.class, args);

	}

}
