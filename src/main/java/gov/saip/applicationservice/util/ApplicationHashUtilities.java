package gov.saip.applicationservice.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class ApplicationHashUtilities {

    private static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

    public static String hashKey(String key) {
        return encoder.encode(key);
    }

    public static boolean verifyKey(String rawKey, String hashedKey) {
        return encoder.matches(rawKey, hashedKey);
    }
}
