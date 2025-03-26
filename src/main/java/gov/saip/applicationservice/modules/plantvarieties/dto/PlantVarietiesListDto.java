package gov.saip.applicationservice.modules.plantvarieties.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class PlantVarietiesListDto {

    private Long plantDetailsId;
    private Long applicationInfoId;
    private String titleAr;
    private String titleEn;
    private String applicationNumber;
    private String applicationRequestNumber;
    private LocalDateTime filingDate;
    private String ipsStatusDescEn;
    private String ipsStatusDescAr;
    private String ipsStatusDescArExternal;
    private String ipsStatusDescEnExternal;
    private LocalDateTime createdDate;
    private String statusCode;

}
