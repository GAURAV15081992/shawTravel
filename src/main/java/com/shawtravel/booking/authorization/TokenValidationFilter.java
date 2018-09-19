package com.shawtravel.booking.authorization;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import com.shawtravel.booking.constants.AppConstant;

@Component
@PropertySource("classpath:config.properties")
public class TokenValidationFilter implements Filter{

	private static final Logger logger = Logger.getLogger(TokenValidationFilter.class);
	private Environment env;
	
	TokenValidationFilter(Environment env){
		this.env=env;
	}
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		logger.debug("Inside TokenValidationFilter init() ");
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		//System.out.println("TEST");
		logger.debug("Inside TokenValidationFilter doFilter() ");
		HttpServletRequest req=(HttpServletRequest) request;
		HttpServletResponse res=(HttpServletResponse) response; 
		
		// Get the HTTP Authorization header from the request
		final String authHeader = req.getHeader("Authorization");
		
		if(AppConstant.PANNET_AUTHORIZATION.equals(authHeader)){
			chain.doFilter(request, response); 
			return;	
		}
		else 
		{
			res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
			res.getWriter().write("{\n\"code\":\""+AppConstant.UNAUTHORIZED_EXCEPTION+"\",\n\"message\":\""+AppConstant.INVALID_AUTHORIZATION_HEADER+"\",\n\"status\":\""+AppConstant.FAIL+"\"\n}");
	        return;
		}
	}

	@Override
	public void destroy() {
		logger.debug("Inside TokenValidationFilter destroy() ");
	}
}

