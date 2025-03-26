package gov.saip.applicationservice.modules.plantvarieties.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.DocumentWithTypeDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import gov.saip.applicationservice.report.dto.UserLightDto;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class PlantVarietyRequestDto extends BaseDto<Long> {

    private Long id;
    private String applicationNumber;
    private Long applicationId;
    private String otherDetails;
    private String ipsStatusDescEn;
    private String ipsStatusDescAr;

    private String applicationStatusCode;
    private String titleAr;
    private String titleEn;
    private String ownerNameAr;
    private String ownerNameEn;

    private LocalDateTime createdDate;
    private ApplicationCustomerType createdByCustomerType;
    private CustomerSampleInfoDto agentInfo;
    private DocumentDto authorizationDocument;
    private RequestTasksDto requestTasksDto;
    //private UserLightDto userLightDto;
    private String ipsStatusDescEnExternal;
    private String ipsStatusDescArExternal;
    private LocalDateTime modifiedDate;

    private Map<String, List<DocumentWithTypeDto>> documentDtoMap;

    public PlantVarietyRequestDto(Long id, String applicationNumber,Long applicationId,
                                  String otherDetails,String ipsStatusDescEn,String ipsStatusDescAr,
                                  String applicationStatusCode,String titleAr,String titleEn,
                                  LocalDateTime createdDate,ApplicationCustomerType createdByCustomerType,String ownerNameAr,String ownerNameEn,
                                  String ipsStatusDescEnExternal,String ipsStatusDescArExternal,LocalDateTime modifiedDate){
        this.id=id;
        this.applicationNumber=applicationNumber;
        this.applicationId = applicationId;
        this.otherDetails=otherDetails;
        this.ipsStatusDescEn=ipsStatusDescEn;
        this.ipsStatusDescAr=ipsStatusDescAr;
        this.applicationStatusCode=applicationStatusCode;
        this.titleAr=titleAr;
        this.titleEn=titleEn;

        this.createdDate=createdDate;
        this.createdByCustomerType=createdByCustomerType;

        this.ownerNameAr = ownerNameAr;
        this.ownerNameEn= ownerNameEn;

        this.ipsStatusDescEnExternal=ipsStatusDescEnExternal;
        this.ipsStatusDescArExternal=ipsStatusDescArExternal;
        this.modifiedDate=modifiedDate;

    }

}
