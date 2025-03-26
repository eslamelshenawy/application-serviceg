package gov.saip.applicationservice.handlers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class ResponseHandler {

    private ResponseHandler() {
    }

    public static ResponseEntity<Object> generateResponse(String message, HttpStatus status, Object responseObj) {
        Map<String, Object> map = new HashMap<>();
        map.put("message", message);
        map.put("status", status.value());
        if (message.toLowerCase(Locale.ROOT).contains("error")) {
            map.put("errors", responseObj);

        } else {
            map.put("data", responseObj);
        }

        return new ResponseEntity<>(map, status);
    }
}