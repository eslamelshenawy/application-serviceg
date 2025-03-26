package gov.saip.applicationservice.common.dto.supportService;


import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.common.model.LkApplicationPriorityStatus;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class ApplicationPriorityModifyRequestDetailsDto extends BaseDto<Long> {

    private Long countryId;

    private CountryDto country;

    private String priorityApplicationNumber;

    private LocalDate filingDate;

    private String applicationClass;

    private DocumentDto priorityDocument;

    private DocumentDto translatedDocument;

    private String dasCode;

    private boolean isExpired;

    private boolean provideDocLater;

    private LkApplicationPriorityStatus priorityStatus;

    private String comment;
}