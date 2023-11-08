package oes.moduleName;

import java.util.Arrays;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class WebProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebProjectApplication.class, args);
	}
}