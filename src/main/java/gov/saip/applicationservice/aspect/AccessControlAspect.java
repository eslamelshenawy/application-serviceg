package gov.saip.applicationservice.aspect;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.util.ApplicationValidator;
import gov.saip.applicationservice.util.SupportServiceValidator;
import lombok.AllArgsConstructor;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;

import static gov.saip.applicationservice.util.Constants.NON_OWNER_SUPPORT_SERVICE_STRING_CODES;

@Aspect
@Component
@AllArgsConstructor
public class AccessControlAspect {
    private final ApplicationValidator applicationValidator;
    private final SupportServiceValidator supportServiceValidator;
    @Before("@annotation(checkCustomerAccess)")
    public void checkAccess(JoinPoint joinPoint, CheckCustomerAccess checkCustomerAccess){
        String categoryCode = getCategoryCode(joinPoint, checkCustomerAccess);
        if(skipValidation(categoryCode)) return;
        switch(checkCustomerAccess.type()){
            case APPLICATION:
                applicationFlow(joinPoint, checkCustomerAccess);
                break;
            case SUPPORT_SERVICES:
                supportServiceFlow(joinPoint, checkCustomerAccess);
                break;
        }
    }

    private String getCategoryCode(JoinPoint joinPoint, CheckCustomerAccess checkCustomerAccess) {
        int categoryCodeParamIndex = (int) checkCustomerAccess.categoryCodeParamIndex();
        String categoryCode = null;
        if (categoryCodeParamIndex != -1) {
            Object[] args = joinPoint.getArgs();
            if (categoryCodeParamIndex < args.length) {
                categoryCode = (String) args[categoryCodeParamIndex];
            }
        }
        return categoryCode;
    }

    private boolean skipValidation(String categoryCode) {
        return isPublicApi() || isValidSkipCode(categoryCode);
    }

    private static boolean isValidSkipCode(String categoryCode) {
        return categoryCode != null && NON_OWNER_SUPPORT_SERVICE_STRING_CODES.contains(categoryCode);
    }

    public boolean isPublicApi() {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (attributes != null) {
            HttpServletRequest request = attributes.getRequest();
            String fullUrl = request.getRequestURL().toString();
            return fullUrl.contains("/pb");
        }
        return false;
    }

    private void supportServiceFlow(JoinPoint joinPoint, CheckCustomerAccess checkCustomerAccess) {
        int supportServiceIdParamIndex = (int) checkCustomerAccess.supportServiceIdParamIndex();
        Long supportServiceId = (Long)joinPoint.getArgs()[supportServiceIdParamIndex];
        supportServiceValidator.checkAccessForCurrentCustomerCode(supportServiceId);
    }


    private void applicationFlow(JoinPoint joinPoint, CheckCustomerAccess checkCustomerAccess) {
        int applicationIdParamIndex = (int) checkCustomerAccess.applicationIdParamIndex();
        Long applicationId = (Long) joinPoint.getArgs()[applicationIdParamIndex];
        applicationValidator.checkAccessForCustomerCode(applicationId);
    }
}
