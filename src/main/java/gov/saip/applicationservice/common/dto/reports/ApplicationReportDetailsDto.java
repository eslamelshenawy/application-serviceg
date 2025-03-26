package gov.saip.applicationservice.common.dto.reports;

import gov.saip.applicationservice.common.dto.ApplicantsDto;
import gov.saip.applicationservice.common.dto.CustomerSampleInfoDto;
import gov.saip.applicationservice.common.dto.DocumentDto;
import gov.saip.applicationservice.common.dto.LkFastTrackExaminationTargetAreaDto;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Data
@Getter
@Setter
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class ApplicationReportDetailsDto implements Serializable {
List<ApplicantsDto> applicants;
    ApplicantsDto mainApplicant;
    List<ApplicantsDto>secondaryApplicant;
 CustomerSampleInfoDto applicationAgent;
}
