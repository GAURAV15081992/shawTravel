package com.shawtravel.booking.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import com.shawtravel.booking.authorization.TokenValidationFilter;

@Configuration
public class FilterBeanConfiguration {

	private TokenValidationFilter tokenValidationFilter;
	
	@Autowired
	FilterBeanConfiguration(Environment env){
		tokenValidationFilter=new TokenValidationFilter(env);
	}
	@Bean
	public FilterRegistrationBean ValidationBean() {
		final FilterRegistrationBean authBean = new FilterRegistrationBean();
		
		authBean.setFilter(tokenValidationFilter);
		authBean.addUrlPatterns("/*");
		
		return authBean;
		
	}
}