package com.chiikawa.demo.common.logging;

import com.chiikawa.demo.common.consatnt.RequestConstant;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Aspect
@Component
@Order(2)
public class LoggingAspect {
    @Autowired
    private LogFormatter formatter;

    @Around("execution(* com.beanny.demo.service..*(..))")
    public Object logServiceMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        // get method name , e.g. listStocks()
        String methodName = joinPoint.getSignature().getName();
        // get class name , e.g. StockService
        String target = joinPoint.getTarget().getClass().getSimpleName();
        long startTime = System.currentTimeMillis();
        String requestId = MDC.get(RequestConstant.REQUEST_ID);
        String httpMethod = MDC.get(RequestConstant.HTTP_METHOD);
        String requestPath = MDC.get(RequestConstant.REQUEST_PATH);

        log.info(formatter.logRequest(requestId,target,methodName,startTime,httpMethod,requestPath));

        try {
            // execute the original method logic
            Object result = joinPoint.proceed();

            long endTime = System.currentTimeMillis();
            // logging
            log.info(formatter.logResponse(requestId,target,methodName,startTime,endTime,httpMethod,requestPath));

            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();

            log.error(formatter.logError(requestId,target,methodName,startTime,endTime,httpMethod,requestPath));

            throw e;
        }
    }

    @Around("execution(* com.beanny.demo.repository..*(..))")
    public Object logRepositoryMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String methodName = joinPoint.getSignature().getName();
        String target = joinPoint.getTarget().getClass().getSimpleName();
        long startTime = System.currentTimeMillis();
        String requestId = MDC.get(RequestConstant.REQUEST_ID);
        String httpMethod = MDC.get(RequestConstant.HTTP_METHOD);
        String requestPath = MDC.get(RequestConstant.REQUEST_PATH);

        log.info(formatter.logRequest(requestId,target,methodName,startTime,httpMethod,requestPath));

        try {
            Object result = joinPoint.proceed();
            long endTime = System.currentTimeMillis();

            log.info(formatter.logResponse(requestId,target,methodName,startTime,endTime,httpMethod,requestPath));

            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();

            log.error(formatter.logError(requestId,target,methodName,startTime,endTime,httpMethod,requestPath));

            throw e;
        }
    }

    @Around("execution(* com.beanny.demo.controller..*(..))")
    public Object logControllerMethods(ProceedingJoinPoint joinPoint) throws Throwable {
        Logger log = LoggerFactory.getLogger(joinPoint.getTarget().getClass());
        String methodName = joinPoint.getSignature().getName();
        String target = joinPoint.getTarget().getClass().getSimpleName();
        long startTime = System.currentTimeMillis();
        String requestId = MDC.get(RequestConstant.REQUEST_ID);
        String httpMethod = MDC.get(RequestConstant.HTTP_METHOD);
        String requestPath = MDC.get(RequestConstant.REQUEST_PATH);

        log.info(formatter.logRequest(requestId,target,methodName,startTime,httpMethod,requestPath));

        try {
            // execute the original method logic
            Object result = joinPoint.proceed();

            long endTime = System.currentTimeMillis();
            // logging
            log.info(formatter.logResponse(requestId,target,methodName,startTime,endTime,httpMethod,requestPath));

            return result;
        } catch (Exception e) {
            long endTime = System.currentTimeMillis();

            log.error(formatter.logError(requestId,target,methodName,startTime,endTime,httpMethod,requestPath));

            throw e;
        }
    }
}