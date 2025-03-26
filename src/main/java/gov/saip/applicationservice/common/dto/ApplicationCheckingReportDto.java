package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.enums.ReportsType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class ApplicationCheckingReportDto extends BaseDto<Long> implements Serializable {
    private Long applicationId;
    private String createdDate;
    private Long documentId;
    @Enumerated(EnumType.STRING)
    private ReportsType reportType;


}
