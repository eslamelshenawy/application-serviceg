package gov.saip.applicationservice.common.dto;

import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class ApplicationSearchDto extends BaseSupportServiceDto {

    private String title;
    private String description;
    private String notes;
    private Long  classificationId;
    private String classificationNameAr;
    private DocumentLightDto applicationSearchDocument;
}
