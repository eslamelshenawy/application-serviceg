package gov.saip.applicationservice.modules.ic.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationLegalDocumentDto extends BaseDto<Long> {
    private ApplicationInfo application;
    private DocumentDto document;
    private String fileName;
}