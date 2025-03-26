package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseLkpEntityDto;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class LkAttributeDto extends BaseLkpEntityDto<Integer> implements Serializable {

}
