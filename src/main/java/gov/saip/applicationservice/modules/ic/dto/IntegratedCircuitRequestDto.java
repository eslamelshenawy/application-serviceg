package gov.saip.applicationservice.modules.ic.dto;

import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.DocumentWithTypeDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import gov.saip.applicationservice.common.enums.ApplicationCustomerType;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@Getter
@Setter
public class IntegratedCircuitRequestDto {

    private Long id;
    private String applicationNumber;
    private Long applicationId;
    private String designNameAr;
    private String designNameEn;
    private String designDescription;
    private LocalDateTime designDate;
    private Boolean commercialExploited;
    private LocalDateTime commercialExploitationDate;
    private Long countryId;
    private CountryDto commercialExploitedCountry;

    private String ipsStatusDescEn;
    private String ipsStatusDescAr;
    private String applicationStatusCode;

    private Boolean notifyChecker;

    private Map<String, List<DocumentWithTypeDto>> documentDtoMap;
    private String approvedNameAr;
    private String approvedNameEn;
    private LocalDateTime createdDate;
    private ApplicationCustomerType createdByCustomerType;
    private CustomerSampleInfoDto agentInfo;

    public IntegratedCircuitRequestDto(Long id, String applicationNumber, Long applicationId, String designNameAr, String designNameEn,
                                       String designDescription, LocalDateTime designDate, Boolean commercialExploited, LocalDateTime commercialExploitationDate,
                                       String ipsStatusDescEn, String ipsStatusDescAr, String applicationStatusCode, Long countryId, String approvedNameAr, String approvedNameEn, LocalDateTime createdDate, ApplicationCustomerType createdByCustomerType) {
        this.id = id;
        this.applicationNumber = applicationNumber;
        this.applicationId = applicationId;
        this.designNameAr = designNameAr;
        this.designNameEn = designNameEn;
        this.designDescription = designDescription;
        this.designDate = designDate;
        this.commercialExploited = commercialExploited;
        this.commercialExploitationDate = commercialExploitationDate;
        this.ipsStatusDescEn = ipsStatusDescEn;
        this.ipsStatusDescAr = ipsStatusDescAr;
        this.applicationStatusCode = applicationStatusCode;
        this.countryId = countryId;
        this.approvedNameAr = approvedNameAr;
        this.approvedNameEn = approvedNameEn;
        this.createdDate = createdDate;
        this.createdByCustomerType = createdByCustomerType;
    }
}
