package gov.saip.applicationservice.filters;


import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.exception.ApiExceptionDto;
import gov.saip.applicationservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAccessDeniedHandler implements AccessDeniedHandler {

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        ApiExceptionDto apiException = new ApiExceptionDto(
                Constants.ErrorKeys.ERROR_UNAUTHORIZED,
                Constants.ErrorKeys.ERROR_UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("Z")).toString()
        );
        //log.info("Request Url iss ==>> {}" , request.getRequestURI());

        ObjectMapper mapper = new ObjectMapper();
        ApiResponse apiResponse = ApiResponse.builder().error(apiException).success(false)
                .code(HttpStatus.UNAUTHORIZED.value()).build();
        String message = mapper.writeValueAsString(apiResponse);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(message);
    }
}
