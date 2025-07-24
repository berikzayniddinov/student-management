package com.example.homework.aspects;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@Aspect
@Component
public class LoggingAspect {

    private final Logger logger = Logger.getLogger(LoggingAspect.class.getName());

    @Pointcut("execution(* com.example.homework.service.*.*(..))")
    public void serviceMethodsPointcut() {}

    @Around("serviceMethodsPointcut()")
    public Object logServiceMethodExecution(@org.jetbrains.annotations.NotNull ProceedingJoinPoint joinPoint) throws Throwable {
        String methodName = joinPoint.getSignature().getName();
        String className = joinPoint.getTarget().getClass().getName();

        long startTime = System.currentTimeMillis();
        logger.info("Entering method: " + className + "." + methodName + "()");

        Object result;
        try {
            result = joinPoint.proceed();
        } catch (Throwable e) {
            long endTime = System.currentTimeMillis();
            logger.severe("Method " + className + "." + methodName + "() threw exception in " + (endTime - startTime) + "ms: " + e.getMessage());
            throw e;
        }

        long endTime = System.currentTimeMillis(); //fdhsgf
        logger.info("Exiting method: " + className + "." + methodName + "() in " + (endTime - startTime) + "ms");

        return result;
    }
}