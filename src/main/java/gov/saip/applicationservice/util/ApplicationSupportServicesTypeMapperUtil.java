package gov.saip.applicationservice.util;

import gov.saip.applicationservice.common.model.ApplicationSupportServicesType;
import gov.saip.applicationservice.common.service.ApplicationSupportServicesTypeService;
import lombok.AllArgsConstructor;
import org.mapstruct.Named;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class ApplicationSupportServicesTypeMapperUtil {
    private final ApplicationSupportServicesTypeService applicationSupportServicesTypeService;

    @Named("mappingApplicationSupportServicesTypeEntity")
    public ApplicationSupportServicesType findApplicationSupportServicesTypeServiceById(Long id){
        return applicationSupportServicesTypeService.findById(id);
    }
}
