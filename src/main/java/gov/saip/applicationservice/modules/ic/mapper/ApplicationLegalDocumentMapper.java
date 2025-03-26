package gov.saip.applicationservice.modules.ic.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.modules.ic.dto.ApplicationLegalDocumentDto;
import gov.saip.applicationservice.modules.ic.dto.LegalDocumentListDto;
import gov.saip.applicationservice.modules.ic.model.ApplicationLegalDocument;
import org.mapstruct.Mapper;

@Mapper
public interface ApplicationLegalDocumentMapper extends BaseMapper<ApplicationLegalDocument, ApplicationLegalDocumentDto> {

    LegalDocumentListDto mapToLegalDocumentListDto(ApplicationLegalDocument entity);
}
