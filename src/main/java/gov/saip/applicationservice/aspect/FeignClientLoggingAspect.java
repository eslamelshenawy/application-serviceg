package gov.saip.applicationservice.aspect;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

//@Aspect
//@Component
public class FeignClientLoggingAspect {
    private static final Logger LOGGER = LoggerFactory.getLogger(FeignClientLoggingAspect.class);
    @Around("execution(* gov.saip.applicationservice.common.clients.rest.feigns..*(..))")
    public Object AddLoggingAroundFeignClients(ProceedingJoinPoint joinPoint) throws Throwable {
        LOGGER.info("Calling feign client method {}", joinPoint.getSignature().getName());
        Object returnValue = joinPoint.proceed();
        LOGGER.info("Done calling feign client method {}", joinPoint.getSignature().getName() );
        return returnValue;
    }

}
