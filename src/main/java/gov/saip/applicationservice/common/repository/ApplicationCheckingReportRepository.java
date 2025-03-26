package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.ApplicationCheckingReportDto;
import gov.saip.applicationservice.common.enums.ReportsType;
import gov.saip.applicationservice.common.model.ApplicationCheckingReport;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface ApplicationCheckingReportRepository extends BaseRepository<ApplicationCheckingReport, Long> {

    ApplicationCheckingReport findFirstByApplicationInfoIdAndReportTypeOrderByCreatedDateDesc(Long applicationId, ReportsType reportsType);
    Optional<ApplicationCheckingReport> findByApplicationInfoIdAndReportType(Long applicationId, ReportsType reportsType);

    Optional<ApplicationCheckingReport> findBySupportServicesTypeIdAndReportType(Long serviceId ,ReportsType reportsType);



}
