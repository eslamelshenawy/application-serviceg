package gov.saip.applicationservice.common.dto;

import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
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
public class PublicationSchedulingConfigCreateDto {
    @NotNull
    private PublicationFrequency publicationFrequency;
    @NotNull
    private ApplicationCategoryEnum applicationCategorySaipCode;
    @NotEmpty
    private Set<@Valid PublicationTimeCreateDto> publicationTimes;
}
