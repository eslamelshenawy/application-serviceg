package gov.saip.applicationservice.modules.plantvarieties.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.modules.plantvarieties.dto.DUSTestingDocumentDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.DUSTestingDocumentListDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.OtherPlantVarietyDocumentsDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.OtherPlantVarietyDocumentsListDto;
import gov.saip.applicationservice.modules.plantvarieties.model.DUSTestingDocument;
import gov.saip.applicationservice.modules.plantvarieties.model.OtherPlantVarietyDocuments;
import org.mapstruct.Mapper;

@Mapper
public interface OtherPlantVarietyDocumentsMapper extends BaseMapper<OtherPlantVarietyDocuments, OtherPlantVarietyDocumentsDto> {
    OtherPlantVarietyDocumentsListDto mapToOtherPlantVarietyDocumentsListDto(OtherPlantVarietyDocuments entity);
}
