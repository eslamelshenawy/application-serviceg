package gov.saip.applicationservice.common.service.veena;

import gov.saip.applicationservice.base.service.BaseLkService;
import gov.saip.applicationservice.common.dto.veena.LKVeenaDepartmentDto;
import gov.saip.applicationservice.common.model.veena.LKVeenaDepartment;

import java.util.List;


public interface LKVeenaDepartmentService extends BaseLkService<LKVeenaDepartment, Long> {
    List<LKVeenaDepartmentDto> getByVeenaClassificationId(Long classificationId);

}
