package ru.pankov.store.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;

@Aspect
@Component
@Slf4j
public class MethodWorkTime {

    Map<String, Long> map;

    @PostConstruct
    public void init() {
        map = new HashMap<>();
    }

    @Pointcut("execution(public * ru.pankov.store.controller.*.* (..))")
    public void controllers() {}

    @Around("controllers()")
    public Object countCallMethods(ProceedingJoinPoint proceedingJoinPoint) throws Throwable {
        String className = proceedingJoinPoint.getSignature().getDeclaringType().getSimpleName();

        long startTime = System.currentTimeMillis();
        Object result = proceedingJoinPoint.proceed();
        long executionTime = System.currentTimeMillis() - startTime;

        map.computeIfPresent(className, (key, value) -> value + executionTime);
        map.putIfAbsent(className, executionTime);

        return result;
    }

    @PreDestroy
    public void destroy() {
        log.info("Execution controller methods time [ms]");
        log.info(map.toString());
    }

}
