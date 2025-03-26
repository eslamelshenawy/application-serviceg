package gov.saip.applicationservice.common.dto.ApplicationSnapShotDto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDateTime;

@Setter
@Getter
public class ApplicationSnapShotDto extends BaseDto<Long> {
    private Long id;
    private Long applicationId;
    private String createdDateHijri;
    private String applicationInfoDto;
    private String patentRequestDto;
    private String patentDetailDtoWithChangeLogDto;
    private String drawingDto;
    private String pctDto;
    private String protectionElementsDto;
    private Long versionNumber;
}
