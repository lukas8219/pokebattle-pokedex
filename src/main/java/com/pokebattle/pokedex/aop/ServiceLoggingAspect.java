package com.pokebattle.pokedex.aop;

import lombok.RequiredArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Aspect
@Component
@RequiredArgsConstructor
public class ServiceLoggingAspect {

    @Before(value = "within(com.pokebattle.pokedex.service.*)")
    public void logIt(JoinPoint joinPoint) throws Throwable {
        var clazz = joinPoint.getTarget().getClass();
        var logger = LoggerFactory.getLogger(clazz);
        var message = String.format("%s received %s as arguments", clazz.getSimpleName(), Arrays.toString(joinPoint.getArgs()));
        logger.info(message);
    }

}
