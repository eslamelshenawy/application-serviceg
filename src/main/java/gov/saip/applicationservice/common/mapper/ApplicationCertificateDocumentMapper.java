package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.ApplicationCertificateDocumentDto;
import gov.saip.applicationservice.common.model.patent.ApplicationCertificateDocument;
import org.mapstruct.Mapper;


@Mapper
public interface ApplicationCertificateDocumentMapper extends BaseMapper<ApplicationCertificateDocument ,
        ApplicationCertificateDocumentDto> {
}
