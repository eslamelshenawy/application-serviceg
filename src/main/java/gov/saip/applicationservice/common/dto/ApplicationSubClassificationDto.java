package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.enums.SubClassificationType;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Setter
@Getter
public class ApplicationSubClassificationDto {

    private Integer versionId;
    private Long classificationId;
    private SubClassificationType subClassificationType;
    private List<Long> selectedSubClassifications = new ArrayList<>();
    private List<Long> unSelectedSubClassifications = new ArrayList<>();

}
