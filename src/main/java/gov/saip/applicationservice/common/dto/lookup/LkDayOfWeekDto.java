package gov.saip.applicationservice.common.dto.lookup;

import gov.saip.applicationservice.base.dto.BaseLkpEntityDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class LkDayOfWeekDto extends BaseLkpEntityDto<Integer> implements Serializable {
    public LkDayOfWeekDto(Integer integer) {
        super(integer);
    }
}
