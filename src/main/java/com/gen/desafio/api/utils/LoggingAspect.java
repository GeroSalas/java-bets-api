
package com.gen.desafio.api.utils;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * AOP Logging (Transversal)
 */
@Aspect
public class LoggingAspect {

	private static final Logger log = LoggerFactory.getLogger(LoggingAspect.class);

	@Before("execution(public * com.gen.desafio.api.services..*.*(..))")
	public void enterService(JoinPoint joinPoint) {
		log.debug("[" + joinPoint.getSignature().getDeclaringTypeName() +"."+ joinPoint.getSignature().getName() + "]: ENTER");
	}

	@After("execution(public * com.gen.desafio.api.services..*.*(..))")
	public void exitService(JoinPoint joinPoint) {
		log.debug("[" + joinPoint.getSignature().getDeclaringTypeName() +"."+ joinPoint.getSignature().getName() + "]: EXIT");
	}

	@Before("execution(public * com.gen.desafio.api.dal..*.*(..))")
	public void enterDAO(JoinPoint joinPoint) {
		log.debug("[" + joinPoint.getSignature().getDeclaringTypeName() +"."+ joinPoint.getSignature().getName() + "]: ENTER");
	}

	@After("execution(public * com.gen.desafio.api.dal..*.*(..))")
	public void exitDAO(JoinPoint joinPoint) {
		log.debug("[" + joinPoint.getSignature().getDeclaringTypeName() +"."+ joinPoint.getSignature().getName() + "]: EXIT");
	}
	
}
