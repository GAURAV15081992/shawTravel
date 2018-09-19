package com.shawtravel.booking.config;

import javax.sql.DataSource;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;

@Configuration
@PropertySource(value = "classpath:config.properties", ignoreResourceNotFound = true)
public class MysqlConfiguration {
	@Autowired
	private Environment environment;
	
	 	@Bean
		public DataSource getDataSource() {
			BasicDataSource dataSource = new BasicDataSource();	
			String url = "jdbc:mysql://" + environment.getProperty("mysql.host") + ":" + environment.getProperty("mysql.port") 
							+ "/" + environment.getProperty("mysql.database") + "?useSSL=false";
		    dataSource.setDriverClassName("com.mysql.jdbc.Driver");
		    dataSource.setUrl(url);
		    dataSource.setUsername(environment.getProperty("mysql.username"));
		    dataSource.setPassword(environment.getProperty("mysql.password"));
	    return dataSource;
	}

}
