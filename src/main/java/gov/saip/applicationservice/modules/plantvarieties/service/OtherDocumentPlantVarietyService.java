package gov.saip.applicationservice.modules.plantvarieties.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.modules.plantvarieties.dto.DUSTestingDocumentListDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.OtherPlantVarietyDocumentsDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.OtherPlantVarietyDocumentsListDto;
import gov.saip.applicationservice.modules.plantvarieties.model.DUSTestingDocument;
import gov.saip.applicationservice.modules.plantvarieties.model.OtherPlantVarietyDocuments;

import java.util.List;

public interface OtherDocumentPlantVarietyService extends BaseService<OtherPlantVarietyDocuments, Long> {

    Long softDeleteOtherDocumentById(Long id);
    List<OtherPlantVarietyDocumentsListDto> findApplicationOtherPlantVarietyDocumentsByApplicationId(Long applicationId);
}
