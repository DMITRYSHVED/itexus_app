package com.example.miracles_store.aspect;

import com.google.common.base.Stopwatch;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import java.time.Duration;
import java.util.Arrays;
import java.util.Objects;

@Aspect
@Component
public class LogAspect {

    private static final Logger logger = LoggerFactory.getLogger(LogAspect.class);

    @Pointcut("within(@org.springframework.web.bind.annotation.RestController *)")
    public void isControllerLayer() {
    }

    @Pointcut("within(@org.springframework.stereotype.Service *)")
    public void serviceMethods() {
    }

    @Around("serviceMethods()")
    public Object logExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        Stopwatch stopwatch = Stopwatch.createStarted();

        try {
            return joinPoint.proceed();
        } finally {
            stopwatch.stop();
            Duration elapsed = stopwatch.elapsed();
            logger.info("Method {} executed in {} ms", joinPoint.getSignature(), elapsed);
        }
    }

    @Before("isControllerLayer()")
    public void logRequest(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = Objects.requireNonNull(attributes).getRequest();

        logger.info("Request: method={}, uri={}, params={}, body={}",
                request.getMethod(), request.getRequestURI(), request.getParameterMap(), Arrays.toString(joinPoint.getArgs()));
    }

    @Around("isControllerLayer()")
    public Object logResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        Object result = joinPoint.proceed();
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletResponse response = Objects.requireNonNull(attributes).getResponse();

        logger.info("Response: status={}, body={}", Objects.requireNonNull(response).getStatus(), result);
        return result;
    }
}

