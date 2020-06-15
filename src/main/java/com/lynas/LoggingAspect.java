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
    public Object logInputOutput(ProceedingJoinPoint joinPoint) throws Throwable {
        ObjectMapper mapper = new ObjectMapper();
        String className = joinPoint.getTarget().getClass().toString();
        String methodName = joinPoint.getSignature().getName();
        Object[] array = joinPoint.getArgs();
        final Object proceed = joinPoint.proceed();
        Logger log = LoggerFactory.getLogger(className);
        log.info("Input");
        log.info(mapper.writeValueAsString(array));
        log.info("Output");
        log.info(mapper.writeValueAsString(proceed));
        return proceed;
    }

    @Around("execution(* com.lynas.controller.*.*(..) )")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
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
