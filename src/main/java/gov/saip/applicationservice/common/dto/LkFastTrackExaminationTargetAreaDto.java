package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import lombok.*;

import java.io.Serializable;

@Data
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LkFastTrackExaminationTargetAreaDto extends BaseDto<Long> implements Serializable {
    private String descriptionAr;
    private String descriptionEn;
    private Boolean show;
}
