package gov.saip.applicationservice.util.objectmother;

import gov.saip.applicationservice.common.dto.PublicationSchedulingConfigCreateDto;
import gov.saip.applicationservice.common.dto.PublicationTimeCreateDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.PublicationFrequency;

import java.util.Set;

public class PublicationSchedulingConfigTestFactory {

    public static PublicationSchedulingConfigCreateDto.PublicationSchedulingConfigCreateDtoBuilder aDefaultWeeklyConfigCreateDto(ApplicationCategoryEnum applicationCategory,
                                                                                                                                 Set<PublicationTimeCreateDto> publicationTimes) {
        return PublicationSchedulingConfigCreateDto.builder()
                .applicationCategorySaipCode(applicationCategory)
                .publicationFrequency(PublicationFrequency.WEEKLY)
                .publicationTimes(publicationTimes);
    }


    public static PublicationSchedulingConfigCreateDto.PublicationSchedulingConfigCreateDtoBuilder aDefaultMonthlyConfigCreateDto(ApplicationCategoryEnum applicationCategory,
                                                                                                                                  Set<PublicationTimeCreateDto> publicationTimes) {
        return PublicationSchedulingConfigCreateDto.builder()
                .applicationCategorySaipCode(applicationCategory)
                .publicationFrequency(PublicationFrequency.MONTHLY)
                .publicationTimes(publicationTimes);
    }
}
