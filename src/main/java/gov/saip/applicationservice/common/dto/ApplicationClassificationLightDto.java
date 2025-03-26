package gov.saip.applicationservice.common.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class ApplicationClassificationLightDto {
    private Long id;
    private List<ClassificationLightDto> classifications = new ArrayList<>();

}
