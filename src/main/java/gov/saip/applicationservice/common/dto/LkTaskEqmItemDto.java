package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseLkpEntityDto;
import gov.saip.applicationservice.common.enums.eqm.EqmRatingValueType;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

@Setter
@Getter
public class LkTaskEqmItemDto extends BaseLkpEntityDto<Integer> implements Serializable {

   private EqmRatingValueType ratingValueType;
   private String saipCode;
   List<LkTaskEqmTypeDto> types;
   private Boolean shown = Boolean.FALSE;

}
