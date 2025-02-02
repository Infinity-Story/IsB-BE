package com.infinity.isbbe;

import com.infinity.isbbe.config.JwtConfig;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtConfig.class)
public class IsBBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(IsBBeApplication.class, args);
	}

}
