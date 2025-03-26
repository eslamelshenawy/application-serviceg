package gov.saip.applicationservice.aspect;

import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class MeasurementAspect {
	
	
private static final Logger LOGGER = LoggerFactory.getLogger(MeasurementAspect.class);
	
    @Around("execution(* gov.saip.applicationservice.common..controllers..*(..))")
    public Object measureExecutionTime(ProceedingJoinPoint joinPoint) throws Throwable {
        long startTime = System.currentTimeMillis();
        StringBuilder sb = new StringBuilder("KPI:");
        sb.append("[").append(joinPoint.getKind())
                .append("]\tfor: ").append(joinPoint.getSignature())
                .append("\twithArgs: ").append("(")
                .append(StringUtils.join(joinPoint.getArgs(), ","))
                .append(")");
        sb.append("\ttook: ");
        Object returnValue = joinPoint.proceed();
        long timeMs = System.currentTimeMillis() - startTime;
        if (timeMs < 500)
             LOGGER.info(sb.append(timeMs).append(" ms.").toString());
        else
            LOGGER.error(sb.append(timeMs).append(" ms.  ==>> please review this service ").toString());
        return returnValue;

    }

}
