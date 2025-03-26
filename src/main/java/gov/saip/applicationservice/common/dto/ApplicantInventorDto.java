package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class ApplicantInventorDto {

    private Long appInfoId;
    private Set<Long> applicants;
    private Boolean isApplicantInventor;
}
