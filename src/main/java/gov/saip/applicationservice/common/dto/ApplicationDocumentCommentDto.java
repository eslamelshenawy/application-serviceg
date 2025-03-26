package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Setter
@Getter
public class ApplicationDocumentCommentDto extends BaseDto<Long> {

    private DocumentLightDto document;
    private List<DocumentLightDto> commentDocuments;
    private List<Long> commentDocumentIds;
    private long pageNumber;
    private long paragraphNumber;
    private String comment;
    private LocalDateTime createdDate;
    private String createdByUser;
}
