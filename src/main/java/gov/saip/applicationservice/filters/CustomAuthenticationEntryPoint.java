package gov.saip.applicationservice.filters;

import com.fasterxml.jackson.databind.ObjectMapper;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.exception.ApiExceptionDto;
import gov.saip.applicationservice.util.Constants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Slf4j
public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e)
            throws IOException, ServletException {

//        log.info("Request Url iss ==>> {}" , request.getRequestURI());
        ApiExceptionDto apiException = new ApiExceptionDto(
                Constants.ErrorKeys.ERROR_UNAUTHORIZED,
                Constants.ErrorKeys.ERROR_UNAUTHORIZED,
                HttpStatus.UNAUTHORIZED,
                ZonedDateTime.now(ZoneId.of("Z")).toString()
        );


        ObjectMapper mapper = new ObjectMapper();
        ApiResponse apiResponse = ApiResponse.builder().error(apiException).success(false)
                .code(HttpStatus.UNAUTHORIZED.value()).build();
        String message = mapper.writeValueAsString(apiResponse);

        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType(APPLICATION_JSON_VALUE);
        response.getWriter().write(message);
    }
}
