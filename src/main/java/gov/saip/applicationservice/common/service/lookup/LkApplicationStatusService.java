package gov.saip.applicationservice.common.service.lookup;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.lookup.LkApplicationStatusDto;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public interface LkApplicationStatusService extends BaseService<LkApplicationStatus, Long> {

    LkApplicationStatus findByCode(String code);

    LkApplicationStatus findByCodeAndApplicationCategory (String code , Long categoryId);


    List<LkApplicationStatus> getStatusByCategory(String code);

    LkApplicationStatus getStatusByApplicationId(Long id);

    PaginationDto findAllApplicationStatusByAppCategory(Integer page, Integer limit, String sortableColumn, Long applicationCategoryId, String search);

    LkApplicationStatus getStatusByCode(String code);
    LkApplicationStatus getStatusByCodeAndApplicationId(String code, Long applicationId);
    List<LkApplicationStatusDto> getStatusGrouped(String code);
    List<LkApplicationStatusDto> getStatusGroupedInternal(String code);


}
