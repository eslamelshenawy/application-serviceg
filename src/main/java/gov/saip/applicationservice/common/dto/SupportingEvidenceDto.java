package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class SupportingEvidenceDto  extends BaseDto<Long> {

    private String patentNumber;

    private String address;

    private String evidenceDate;

    private String classification;

    private String link;

    private Boolean patentRegisteration;

    private DocumentDto document;

    private Long substantiveExaminationReportsId;

    private Long applicationInfoId;
}
