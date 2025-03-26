package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ApplicationInfoListDto extends BaseDto<Long> {
    private Long serial;
    private String applicationNumber;
    private String titleEn;
    private String titleAr;
    private Boolean partialApplication;
    private String partialApplicationNumber;
    private ApplicationStatusDto applicationStatus;
    private String customerCode;
    private List<DocumentDto> documents;


}
