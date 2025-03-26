package gov.saip.applicationservice.common.mapper;

import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkDayOfWeek;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationCategoryRepository;
import gov.saip.applicationservice.common.repository.lookup.LkDayOfWeekRepository;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.DayOfWeek;

@Mapper
public class LkMapper {
    @Autowired
    private LkApplicationCategoryRepository lkApplicationCategoryRepository;

    @Autowired
    private LkDayOfWeekRepository lkDayOfWeekRepository;

    public LkApplicationCategory fromSaipCode(ApplicationCategoryEnum applicationCategorySaipCode) {
        if (applicationCategorySaipCode == null) return null;
        return lkApplicationCategoryRepository.findBySaipCode(applicationCategorySaipCode.name()).orElseThrow();
    }

    public LkDayOfWeek fromCode(DayOfWeek code) {
        if (code == null) return null;
        return lkDayOfWeekRepository.findByCode(code.name()).orElseThrow();
    }
}
