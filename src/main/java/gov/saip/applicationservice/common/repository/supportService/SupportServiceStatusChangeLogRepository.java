package gov.saip.applicationservice.common.repository.supportService;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.supportService.SupportServiceStatusChangeLog;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface SupportServiceStatusChangeLogRepository extends BaseRepository<SupportServiceStatusChangeLog, Long> {

    List<SupportServiceStatusChangeLog> findBySupportServicesTypeId(Long appId);
}
