package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignDetailDto;
import gov.saip.applicationservice.common.dto.reports.ApplicationReportDetailsDto;
import gov.saip.applicationservice.report.dto.IndustrialDesignImagesDetailsDto;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestRejectionNotificationDto {

    private IndustrialDesignDetailDto industrialDesignDetailDto;
    private List<gov.saip.applicationservice.report.dto.IndustrialDesignImagesDetailsDto> IndustrialDesignImagesDetailsDto;
    private List<ClassificationLightDto> classificationDto;
    private String ApplicationNumber;
    private Long count;
    private LocalDateTime appFilingDate;
    private ApplicationReportDetailsDto applicationReportDetailsDto;
    private List<String> examinerNames;
    private String titleEn;
    private String titleAr;
    private String categoryDescAr;

}
