package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.model.LkApplicationPriorityStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class ApplicationPriorityResponseDto {

    private Long id;


    private String priorityApplicationNumber;

    private Long countryId;
    private String countryNameEn;
    private String countryNameAr;

    private Boolean isExpired;


    private LocalDate filingDate;


    private String applicationClass;


    private Boolean provideDocLater;

    private DocumentDto priorityDocument;


    private DocumentDto translatedDocument;


    private String dasCode;


    private LkApplicationPriorityStatus priorityStatus;
    private String comment;
    private int isDeleted;

}
