package gov.saip.applicationservice.modules.plantvarieties.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.modules.plantvarieties.dto.DUSTestingDocumentDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.DUSTestingDocumentListDto;
import gov.saip.applicationservice.modules.plantvarieties.model.DUSTestingDocument;

import java.util.List;

public interface DUSTestingDocumentPlantVarietyService extends BaseService<DUSTestingDocument, Long> {

    Long softDeleteDusTestDocumentById(Long id);
    List<DUSTestingDocumentListDto> findApplicationDusTestDocumentsByApplicationId(Long applicationId);
}
