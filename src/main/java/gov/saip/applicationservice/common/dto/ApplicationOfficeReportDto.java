package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.lookup.LkExaminationOfficeDto;
import gov.saip.applicationservice.common.model.Document;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ApplicationOfficeReportDto extends BaseDto<Long> {

    private Long applicationId;
    private DocumentLightDto document;
    private LkExaminationOfficeDto office;

}
