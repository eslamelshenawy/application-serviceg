package gov.saip.applicationservice.common.service.veena;

import gov.saip.applicationservice.base.service.BaseLkService;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.veena.LKVeenaClassificationDto;
import gov.saip.applicationservice.common.model.veena.LKVeenaClassification;

import java.util.List;


public interface LKVeenaClassificationService extends BaseLkService<LKVeenaClassification, Long> {

    List<LKVeenaClassificationDto>getVeenaClassificationsCodeByApplicationId(Long appId);

    PaginationDto getAllVeenaClassificationsBySearch(Integer page, Integer limit, String sortableColumn, String search, Long applicationCategoryId);
}
