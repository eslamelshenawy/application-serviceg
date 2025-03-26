package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.base.dto.BaseDto;
import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.enums.PublicationFrequency;
import lombok.*;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PublicationSchedulingConfigViewDto extends BaseDto<Long> {
    @NotNull
    private PublicationFrequency publicationFrequency;
    @NotNull
    private LKApplicationCategoryDto applicationCategory;
    @NotEmpty
    private Set<@Valid PublicationTimeViewDto> publicationTimes;
}
