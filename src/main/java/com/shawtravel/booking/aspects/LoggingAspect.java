package com.shawtravel.booking.aspects;

import org.apache.log4j.Logger;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {
	
	private static final Logger logger = Logger.getLogger(LoggingAspect.class);
	
	@Pointcut("within(com.shawtravel.booking..*)")
	protected void allMethod() {
	}
	
	@Before("allMethod()")
    public void logBefore(JoinPoint joinPoint) {
        logger.debug("Start method: " + joinPoint.getSignature().getName());
    }
	
	@After("allMethod()")
    public void logAfter(JoinPoint joinPoint) {
        logger.debug("End method: " + joinPoint.getSignature().getName());
    }
}
