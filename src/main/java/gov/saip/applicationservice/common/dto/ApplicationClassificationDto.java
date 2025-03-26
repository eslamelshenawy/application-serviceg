package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.dto.veena.ApplicationVeenaClassificationDto;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
public class ApplicationClassificationDto {
    private Long id;
    private String applicationNumber;
    private Boolean nationalSecurity;
    private Long classificationUnitId;
    private String classificationNotes;
    private List<ApplicationVeenaClassificationDto> veenaClassifications;
    private List<ClassificationDto> classifications;
    private List<Long> classificationIds;
    private LkClassificationUnitDto classificationUnit;
}
