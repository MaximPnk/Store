package ru.pankov.store.aop;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Aspect
@Component
@Slf4j
public class CountMethodCalling {

    Map<String, Integer> map;

    @PostConstruct
    public void init() {
        map = new HashMap<>();
    }

    @Pointcut("execution(public * ru.pankov.store..* (..))")
    public void fullProject() {}

    @Before("fullProject()")
    public void countCallMethods(JoinPoint joinPoint) {
        map.computeIfPresent(joinPoint.getSignature().toShortString(), (key, value) -> value + 1);
        map.putIfAbsent(joinPoint.getSignature().toShortString(), 1);
    }

    @PreDestroy
    public void destroy() {
        log.info("3 most popular methods:");
        log.info(map.entrySet().stream().sorted((e1, e2) -> e2.getValue() - e1.getValue()).limit(3).collect(Collectors.toList()).toString());
    }

}
