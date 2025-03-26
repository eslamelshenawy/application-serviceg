package gov.saip.applicationservice.report.dto;

import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.ApplicationDocumentLightDto;
import gov.saip.applicationservice.common.dto.ClassificationLightDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignDetailDto;
import gov.saip.applicationservice.common.dto.reports.ApplicationReportDetailsDto;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class IndustrialDesignJasperReportDto implements Serializable {
    private IndustrialDesignDetailDto industrialDesignDetailDto;
    private List<IndustrialDesignImagesDetailsDto> IndustrialDesignImagesDetailsDto;
    private List<ClassificationLightDto> classificationDto;
    private String ApplicationNumber;
    private Long count;
    private LocalDateTime appFilingDate;
    private String  appFilingDateHigiri;
    private LocalDateTime granteDate;
    private String  granteDateHigiri;
   private ApplicationReportDetailsDto applicationReportDetailsDto;
   private List<String> examinerNames;
   private String titleEn;
   private String titleAr;
   private String categoryDescAr;
}
