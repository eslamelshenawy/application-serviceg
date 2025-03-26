package gov.saip.applicationservice.util;

public class StringUtil {
    public static String getTextValueOrEmptyValue(String txt){
        return txt != null ? txt : "";
    }
}
