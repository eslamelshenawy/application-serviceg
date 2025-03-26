package gov.saip.applicationservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Slf4j
public class JsonUtils {
    private final static Logger logger = LoggerFactory.getLogger(JsonUtils.class);
    public static String convertToJson(Object dto) {
        String json = null;
        try {
            json = new ObjectMapper().findAndRegisterModules().writeValueAsString(dto);
        } catch (JsonProcessingException e) {
            logger.error(" error while write json");
            throw new RuntimeException(e);
        }
        return json;
    }

    private  static ObjectMapper objectMapper;

    private JsonUtils() {}

    public static ObjectMapper getObjectMapper() {
        if (objectMapper == null) {
            objectMapper = new ObjectMapper();
            objectMapper.findAndRegisterModules().registerModule(new JavaTimeModule());
        }
        return objectMapper;
    }

    public static String convertToJSONString(Object obj)  {
        if (obj == null) {
            return "";
        }
        try {
            return getObjectMapper().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return "";
        }
    }

}
