package gov.saip.applicationservice.common.enums.certificate;

import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.SneakyThrows;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CertificateRequestPreCondition {
    private static final Map<Long, Class<? extends Enum<?>>> categoryCertificatePreConditions = new HashMap<>();

    static {
        categoryCertificatePreConditions.put(1L, PatentCertificatePreCondition.class);
        categoryCertificatePreConditions.put(5L, TrademarkCertificatePreCondition.class);
        categoryCertificatePreConditions.put(2L, IndustrialCertificatePreCondition.class);
    }

    public static Class<? extends Enum<?>> getValueForCondition(Long condition) {
        return categoryCertificatePreConditions.get(condition);
    }
    
    @SneakyThrows
    public static List<ApplicationStatusEnum> getApplicationStatusListByCategoryIdAndType(Long categoryId, String type) {
        Class<? extends Enum<?>> enumClass = CertificateRequestPreCondition.getValueForCondition(categoryId);
        if (enumClass != null) {
            try {
                Enum<?> preConditionEnum = Enum.valueOf(enumClass.asSubclass(Enum.class), type);
                Method getAppStatusListMethod = enumClass.getMethod("getAppStatusList");
                Object result = getAppStatusListMethod.invoke(preConditionEnum);
                if (result instanceof List) {
                    return (List<ApplicationStatusEnum>) result;
                }
            } catch (Exception e) {
                throw new BusinessException(Constants.ErrorKeys.INVALID_CERTIFICATE);
            }
        }
        return Collections.emptyList();
    }

    
}


