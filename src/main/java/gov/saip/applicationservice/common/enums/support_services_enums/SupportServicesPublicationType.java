package gov.saip.applicationservice.common.enums.support_services_enums;

import gov.saip.applicationservice.common.enums.PublicationType;
import gov.saip.applicationservice.exception.BusinessException;
import gov.saip.applicationservice.util.Constants;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static gov.saip.applicationservice.common.enums.ApplicationCategoryEnum.*;

@AllArgsConstructor
@Getter
public class SupportServicesPublicationType {
    
    private static final Map<String, Class<? extends Enum<?>>> CATEGORY_SUPPORT_SERVICE = new HashMap<>();
    
    static {
        CATEGORY_SUPPORT_SERVICE.put(PATENT.name(), PatentSupportServiceEnum.class);
        CATEGORY_SUPPORT_SERVICE.put(TRADEMARK.name(), TrademarkSupportServiceEnum.class);
        CATEGORY_SUPPORT_SERVICE.put(INDUSTRIAL_DESIGN.name(), IndustrialSupportServiceEnum.class);
    }
    
    public static Class<? extends Enum<?>> getValueForCondition(String condition) {
        return CATEGORY_SUPPORT_SERVICE.get(condition);
    }
    
    @SneakyThrows
    public static List<PublicationType> getPublicationTypesForSupportServices(String categoryCode, String supportServiceCode) {
        Class<? extends Enum<?>> enumClass = SupportServicesPublicationType.getValueForCondition(categoryCode);
        if (enumClass != null) {
            try {
                Enum<?> publicationTypeEnum = Enum.valueOf(enumClass.asSubclass(Enum.class), supportServiceCode);
                Method getPublicationTypeListMethod = enumClass.getMethod("getPublicationTypes");
                Object result = getPublicationTypeListMethod.invoke(publicationTypeEnum);
                if (result instanceof List) {
                    return (List<PublicationType>) result;
                }
            } catch (Exception e) {
                String[] params = {categoryCode, supportServiceCode};
                throw new BusinessException(Constants.ErrorKeys.INVALID_SUPPORT_SERVICE, HttpStatus.NOT_FOUND, params);
            }
        }
        return Collections.emptyList();
    }
    
   
}
