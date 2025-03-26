package gov.saip.applicationservice.util;

import com.github.f4b6a3.tsid.TsidCreator;

public class TSIDUtils {

    public static Long next(){
        return TsidCreator.getTsid().toLong();
    }

    public static String nextString(){
        return TsidCreator.getTsid().toString();
    }
}
