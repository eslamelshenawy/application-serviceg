package gov.saip.applicationservice.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class Util {
    public Object getFromBasicUserinfo(String key) {
        try {
            HashMap<String, Object> basicUserinfo = (HashMap<String, Object>) SecurityContextHolder.getContext().getAuthentication().getDetails();
            return basicUserinfo.getOrDefault(key, null);
        } catch (Exception e) {
            return null;
        }
    }
}
