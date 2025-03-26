package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.ApplicationOfficeReport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationOfficeReportRepository extends BaseRepository<ApplicationOfficeReport, Long> {
    List<ApplicationOfficeReport> findByApplicationInfoId(Long appId);

}
