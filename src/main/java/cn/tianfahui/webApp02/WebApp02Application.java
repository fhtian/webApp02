package cn.tianfahui.webApp02;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class WebApp02Application {

	public static void main(String[] args) {
		SpringApplication.run(WebApp02Application.class, args);
	}	
}
