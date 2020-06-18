package com.lynas;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LoggingAspect {

    @Around("@annotation(com.lynas.LogInputOutput)")
    public Object logInputOutput(ProceedingJoinPoint joinPoint)
            throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String methodName = joinPoint.getSignature().getName();
        Object[] input = joinPoint.getArgs();
        log.info(methodName + "::Input [" + mapper.writeValueAsString(input) + "]");
        Object object = joinPoint.proceed();
        log.info(methodName + "::Output [" + mapper.writeValueAsString(object) + "]");
        return object;
    }

    // See execution time of a method in the controller package
    @Around("execution(* com.lynas.controller.*.*(..) )")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint)
            throws Throwable {
        Long startTime = System.currentTimeMillis();
        String className = joinPoint.getTarget().getClass().toString();
        String methodName = joinPoint.getSignature().getName();
        final Object proceed = joinPoint.proceed();
        Logger log = LoggerFactory.getLogger(className);
        Long endTime = System.currentTimeMillis();
        log.info(methodName + " executed in " + (endTime - startTime) + " ms");
        return proceed;
    }

}
