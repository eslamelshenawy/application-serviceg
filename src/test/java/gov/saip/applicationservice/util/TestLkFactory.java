package gov.saip.applicationservice.util;

import gov.saip.applicationservice.common.dto.lookup.LKApplicationCategoryDto;
import gov.saip.applicationservice.common.dto.lookup.LkDayOfWeekDto;
import gov.saip.applicationservice.common.enums.ApplicationAgentStatus;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.PublicationType;
import gov.saip.applicationservice.common.mapper.LkApplicationCategoryMapper;
import gov.saip.applicationservice.common.mapper.lookup.LkDayOfWeekMapper;
import gov.saip.applicationservice.common.model.LKPublicationType;
import gov.saip.applicationservice.common.model.LkApplicationCategory;
import gov.saip.applicationservice.common.model.LkApplicationStatus;
import gov.saip.applicationservice.common.model.LkDayOfWeek;
import gov.saip.applicationservice.common.repository.lookup.LKPublicationTypeRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationCategoryRepository;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationStatusRepository;
import gov.saip.applicationservice.common.repository.lookup.LkDayOfWeekRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestComponent;

import javax.persistence.EntityManager;
import java.time.DayOfWeek;
import java.util.List;

@TestComponent
@RequiredArgsConstructor
public class TestLkFactory {
    private final LkApplicationCategoryMapper lkApplicationCategoryMapper;
    private final LkApplicationCategoryRepository lkApplicationCategoryRepository;
    private final LkDayOfWeekRepository lkDayOfWeekRepository;
    private final EntityManager entityManager;
    private final LkDayOfWeekMapper lkDayOfWeekMapper;
    private final LkApplicationStatusRepository lkApplicationStatusRepository;
    private final LKPublicationTypeRepository lkPublicationTypeRepository;


    public static LkDayOfWeek createDayOfWeekWithoutId(DayOfWeek day) {
        LkDayOfWeek dayOfWeek = new LkDayOfWeek();
        dayOfWeek.setCode(day.name());
        return dayOfWeek;
    }

    public LkDayOfWeekDto createDayOfWeekDto(DayOfWeek day) {
        return lkDayOfWeekMapper.map(lkDayOfWeekRepository.findByCode(day.name()).orElseThrow());
    }

    public LKApplicationCategoryDto createApplicationCategoryDto(ApplicationCategoryEnum applicationCategoryEnum) {
        return lkApplicationCategoryMapper.map(createApplicationCategory(applicationCategoryEnum));
    }

    public LkApplicationCategory createApplicationCategory(ApplicationCategoryEnum applicationCategoryEnum) {
        return lkApplicationCategoryRepository.findBySaipCode(applicationCategoryEnum.name()).orElseThrow();
    }

    public LkApplicationStatus createApplicationStatus(ApplicationStatusEnum applicationStatusEnum) {
        return lkApplicationStatusRepository.findByCode(applicationStatusEnum.name()).orElseThrow();
    }
    public <T> List<T> findAll(Class<T> clazz) {
        return entityManager.createQuery("""
                FROM %s
                """.formatted(clazz.getSimpleName()), clazz).getResultList();
    }

    public LKPublicationType createPublicationType(PublicationType publicationType) {
        return lkPublicationTypeRepository.findByCode(publicationType.name()).orElseThrow();
    }
}
