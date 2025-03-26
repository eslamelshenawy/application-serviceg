package gov.saip.applicationservice.modules.plantvarieties.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.modules.ic.dto.LegalDocumentListDto;
import gov.saip.applicationservice.modules.ic.model.ApplicationLegalDocument;
import gov.saip.applicationservice.modules.plantvarieties.dto.DUSTestingDocumentDto;
import gov.saip.applicationservice.modules.plantvarieties.dto.DUSTestingDocumentListDto;
import gov.saip.applicationservice.modules.plantvarieties.model.DUSTestingDocument;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;

@Mapper
public interface DUSTestingDocumentMapper extends BaseMapper<DUSTestingDocument, DUSTestingDocumentDto> {
    DUSTestingDocumentListDto mapToDUSTestingDocumentListDto(DUSTestingDocument entity);

    List<DUSTestingDocumentListDto> mapToListOfDUSTestingDocumentListDto(List<DUSTestingDocument> entity);

}
