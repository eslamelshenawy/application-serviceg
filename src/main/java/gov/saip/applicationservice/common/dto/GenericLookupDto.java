package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseLKDto;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
public class GenericLookupDto extends BaseLKDto<Long> {
    public GenericLookupDto(Long id) {
        setId(id);
    }
}
