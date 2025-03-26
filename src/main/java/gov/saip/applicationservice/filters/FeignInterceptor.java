package gov.saip.applicationservice.filters;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import gov.saip.applicationservice.util.Constants;
import gov.saip.applicationservice.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

@Component
@Slf4j
public class FeignInterceptor implements RequestInterceptor {

    @Override
    public void apply(RequestTemplate requestTemplate) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (requestAttributes == null) {
            return;
        }
        HttpServletRequest request = requestAttributes.getRequest();
//        log.info("Request Url is ==>> {}", request.getRequestURI());

        String authorization = request.getHeader("Authorization");
        if (authorization != null && !request.getRequestURI().contains("gp-ipsearch-api-examiner")) {
            requestTemplate.header("Authorization", authorization);
        }
        String mainServiceName = request.getHeader(Constants.AppRequestHeaders.MAIN_SERVICE_NAME.getKey());
        if (mainServiceName == null) {
            requestTemplate.header(Constants.AppRequestHeaders.MAIN_SERVICE_NAME.getKey(), Constants.AppRequestHeaders.MAIN_SERVICE_NAME.getValue());
            requestTemplate.header(Constants.AppRequestHeaders.MAIN_SERVICE_TYPE.getKey(), Constants.AppRequestHeaders.MAIN_SERVICE_TYPE.getValue());
        } else {
            requestTemplate.header(Constants.AppRequestHeaders.MAIN_SERVICE_NAME.getKey(), mainServiceName);
            requestTemplate.header(Constants.AppRequestHeaders.MAIN_SERVICE_TYPE.getKey(), request.getHeader(Constants.AppRequestHeaders.MAIN_SERVICE_TYPE.getKey()));
        }
        requestTemplate.header(Constants.AppRequestHeaders.REQUEST_CORRELATION_ID.getKey(), MDC.get(Constants.REQUEST_CORRELATION_ID));
        requestTemplate.header(Constants.AppRequestHeaders.CUSTOMER_ID.getKey(), Utilities.getCustomerIdFromHeaders());
        requestTemplate.header(Constants.AppRequestHeaders.CUSTOMER_CODE.getKey(), Utilities.getCustomerCodeFromHeaders());
//        requestTemplate.headers().entrySet().stream()
//                .forEach(entry -> {
//                    log.info("Key: {}", entry.getKey());
//                    log.info("Values:");
//                    entry.getValue().forEach(value -> log.info("\t{}", value));
//                });
    }
}
