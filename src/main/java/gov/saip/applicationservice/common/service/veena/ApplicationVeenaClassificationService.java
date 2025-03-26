package gov.saip.applicationservice.common.service.veena;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.veena.ApplicationVeenaClassificationDto;
import gov.saip.applicationservice.common.dto.veena.ApplicationVeenaClassificationRequestDto;
import gov.saip.applicationservice.common.dto.veena.LKVeenaClassificationDto;
import gov.saip.applicationservice.common.model.veena.ApplicationVeenaClassification;

import java.util.List;


public interface ApplicationVeenaClassificationService extends BaseService<ApplicationVeenaClassification, Long> {

    List<LKVeenaClassificationDto> getVeenaClassificationsByAppId(Long appId);
    List<ApplicationVeenaClassificationRequestDto> findByApplicationId(Long appId);

    void deleteAllVeenaClassificationsByAppId(Long applicationId);


}
