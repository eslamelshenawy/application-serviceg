package gov.saip.applicationservice.modules.ic.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LegalDocumentListDto extends BaseDto<Long> {
    private String fileName;
    private DocumentDto document;
}