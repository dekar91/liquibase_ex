package ru.team42.analyzer.config.jwt;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "jwt")
@Data
public class JwtProperties {

	private String secretKey = "secretftyhgugfytr67t67fgjhklfghjkyuyr6r675r7r";

	//validity in milliseconds
	private long validityInMs = 3600000; // 1h
}
