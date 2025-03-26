package gov.saip.applicationservice.common.service.veena;

import gov.saip.applicationservice.base.service.BaseLkService;
import gov.saip.applicationservice.common.dto.veena.LKVeenaAssistantDepartmentDto;
import gov.saip.applicationservice.common.model.veena.LKVeenaAssistantDepartment;
import io.lettuce.core.dynamic.annotation.Param;

import java.util.List;


public interface LKVeenaAssistantDepartmentService extends BaseLkService<LKVeenaAssistantDepartment, Long> {

    List<LKVeenaAssistantDepartmentDto> getByVeenaDepartmentId(Long departmentId);

    List<LKVeenaAssistantDepartmentDto>getVeenaAssistantDepartmentCodeByApplicationId(Long appId);

    List<LKVeenaAssistantDepartmentDto> searchByVeenaAssistantDepartment(String search);

}
