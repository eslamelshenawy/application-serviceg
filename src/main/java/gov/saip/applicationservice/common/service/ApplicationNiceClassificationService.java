package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.annotation.CheckCustomerAccess;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ClassificationDto;
import gov.saip.applicationservice.common.dto.ClassificationLightDto;
import gov.saip.applicationservice.common.dto.ListApplicationClassificationDto;
import gov.saip.applicationservice.common.dto.trademark.ApplicationTradeMarkListClassificationsDto;
import gov.saip.applicationservice.common.model.ApplicationNiceClassification;

import java.util.List;
import java.util.Objects;

public interface ApplicationNiceClassificationService extends BaseService<ApplicationNiceClassification, Long> {


    List<ClassificationDto> listSelectedApplicationNiceClassifications(Long id);

    List<ClassificationLightDto> getLightNiceClassificationsByAppId(Long id);

    void deleteAllByApplicationId(Long id);
    public ApplicationTradeMarkListClassificationsDto getApplicantsAndClassifications(Long appId);

}
