package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseLkpEntityDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@Builder
@AllArgsConstructor
public class LkNoteCategoryDto extends BaseLkpEntityDto<Integer> implements Serializable {

}
