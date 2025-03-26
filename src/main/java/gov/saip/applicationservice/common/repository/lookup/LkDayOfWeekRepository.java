package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.LkAttribute;
import gov.saip.applicationservice.common.model.LkDayOfWeek;
import org.springframework.stereotype.Repository;

@Repository
public interface LkDayOfWeekRepository extends BaseLkRepository<LkDayOfWeek, Integer> {

}
