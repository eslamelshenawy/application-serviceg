package gov.saip.applicationservice.Error;

import com.fasterxml.jackson.databind.ObjectMapper;
import feign.Response;
import feign.Util;
import feign.codec.ErrorDecoder;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.exception.AppErrorDecoderException;
import gov.saip.applicationservice.exception.CamundaErrorDecoderException;
import gov.saip.applicationservice.exception.CamundaErrorResponse;
import gov.saip.applicationservice.exception.ErrorDecoderException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Slf4j
public class CustomErrorDecoder implements ErrorDecoder {

    @Override
    public ErrorDecoderException decode(String methodKey, Response response){
        try {
            if(response == null || response.body() == null )
                return new CamundaErrorDecoderException(new CamundaErrorResponse(), HttpStatus.valueOf(response.status()));

            byte[] body = response.body() != null ? Util.toByteArray(response.body().asInputStream()) : new byte[0];

                ObjectMapper mapper = new ObjectMapper();
                CamundaErrorResponse message =  mapper.readValue(body, CamundaErrorResponse.class);
                if (message.getMessage() != null)
                    return new CamundaErrorDecoderException(message, HttpStatus.valueOf(response.status()));
                ApiResponse apiExceptionDto =  mapper. readValue(body, ApiResponse.class);
                if (apiExceptionDto.getError().getMessage() != null)
                    return new AppErrorDecoderException(apiExceptionDto, HttpStatus.valueOf(response.status()));


        } catch (IOException e) {
            log.error("Error when parse response ==>>{}", e);
            throw new RuntimeException(e);
        }
        return new CamundaErrorDecoderException(new CamundaErrorResponse(), HttpStatus.valueOf(response.status()));
    }
}
