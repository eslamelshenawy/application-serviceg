package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.Document;
import gov.saip.applicationservice.util.Constants;
import lombok.Data;

import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Data
public class ApplicationOtherDocumentDto extends BaseDto<Long> {

    private Long applicationId;
    private String documentName;

    private DocumentLightDto document ;
}
