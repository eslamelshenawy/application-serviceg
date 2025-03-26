package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicantInventorDto;
import gov.saip.applicationservice.common.dto.ApplicantsRequestDto;
import gov.saip.applicationservice.common.dto.ApplicationRelevantRequestsDto;
import gov.saip.applicationservice.common.dto.InventorRequestsDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationRelevantType;
import gov.saip.applicationservice.modules.ic.dto.ApplicationApplicantDto;
import gov.saip.applicationservice.modules.ic.dto.InventorDto;

import java.util.List;

public interface ApplicationRelevantTypeService extends BaseService<ApplicationRelevantType, Long> {

    void updateDocument(Long id, Long documentId);

    Boolean hasApplicationRelavantTypeWithOutWaiverDocument(Long appId);


    ApplicationInfo saveApplicationRelevantAndRelevantTypes(ApplicantsRequestDto applicantsRequestDto, ApplicationInfo dbApplicationInfo);
    ApplicationInfo updateApplicationRelevantAndRelevantTypes(ApplicantsRequestDto applicantsRequestDto, List<String> customersCodes, ApplicationInfo applicationInfoExist);

    Long addInventorPatch(InventorRequestsDto dto);

    List<InventorDto> getAllInventorsExceptApplicantsByApplication(Long applicationId);

    List<InventorDto> getApplicantsByApplication(Long applicationId, Boolean inventor);

    Long softDeleteById(Long id);

    Long addInventor(ApplicationRelevantRequestsDto dto);

    Long updateApplicantInventors(ApplicantInventorDto applicantInventorDto);

    Long addSecondaryApplicant(ApplicationRelevantRequestsDto dto);

    ApplicationApplicantDto getMainApplicantInfoByApplicationId(Long applicationId);

    void updateCustomerCodeForMainApplicationRelevant(String customerCode, Long applicationId);
}
