package com.shawtravel.booking.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath:config.properties", ignoreResourceNotFound = true)
public class ApplicationConfiguration {
	private static final String SERVER_PORT =  "server.port";
	
	@Autowired
	private Environment environment;

	public int getServerPort() {
		return Integer.parseInt(environment.getProperty(SERVER_PORT));
	}

}
