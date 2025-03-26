package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.enums.ApplicationRelevantEnum;
import gov.saip.applicationservice.common.enums.IdentifierTypeEnum;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Data
public class ApplicationRelevantTypeDto {

    private Long id;
    private String customerCode;

    private ApplicationRelevantEnum type;
    private ApplicationRelevantDto applicationRelevant;
    private DocumentDto waiverDocumentId;

    private boolean inventor;

    private int isDeleted;


}
