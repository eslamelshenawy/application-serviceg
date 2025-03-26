package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.common.model.LkApplicationPriorityStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicationPriorityListDto {

    private Long id;


    private String priorityApplicationNumber;

    private Long countryId;

    private CountryDto country;

    private LocalDate filingDate;


    private String applicationClass;


    private Boolean provideDocLater;

    private DocumentLightDto priorityDocument;

    private Boolean isExpired;
    private DocumentLightDto translatedDocument;


    private String dasCode;


    private LkApplicationPriorityStatus priorityStatus;

    private int isDeleted;
    private String comment;

}
