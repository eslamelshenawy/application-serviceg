package gov.saip.applicationservice.common.service.lookup;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.lookup.LkAcceleratedTypeDto;
import gov.saip.applicationservice.common.model.LkAcceleratedType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LkAcceleratedTypeService extends BaseService<LkAcceleratedType, Long> {
    List<LkAcceleratedTypeDto> getAllAcceleratedTypes();
    List<LkAcceleratedTypeDto> filterByApplicationCategory(String appCategoryDescEn);
}
