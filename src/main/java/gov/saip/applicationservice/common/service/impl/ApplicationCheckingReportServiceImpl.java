package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.ApplicationCheckingReportDto;
import gov.saip.applicationservice.common.enums.ReportsType;
import gov.saip.applicationservice.common.mapper.ApplicationCheckingReportMapper;
import gov.saip.applicationservice.common.model.ApplicationCheckingReport;
import gov.saip.applicationservice.common.repository.ApplicationCheckingReportRepository;
import gov.saip.applicationservice.common.service.ApplicationCheckingReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ApplicationCheckingReportServiceImpl extends BaseServiceImpl<ApplicationCheckingReport, Long> implements ApplicationCheckingReportService {

    private final ApplicationCheckingReportRepository reportRepository;
    private final ApplicationCheckingReportMapper reportMapper;
    private final ApplicationCheckingReportRepository applicationCheckingReportRepository;
    @Override
    protected BaseRepository<ApplicationCheckingReport, Long> getRepository() {
        return reportRepository;
    }
    @Override
    public ApplicationCheckingReportDto getLastReportByReportType(Long applicationId, ReportsType reportsType) {
        ApplicationCheckingReport checkingReport = reportRepository.findFirstByApplicationInfoIdAndReportTypeOrderByCreatedDateDesc(applicationId, reportsType);
        return checkingReport==null ? null : reportMapper.map(checkingReport);
    }
    @Override
    public ApplicationCheckingReport findByApplicationInfoIdAndReportType(Long applicationId, ReportsType reportsType) {
        Optional<ApplicationCheckingReport> applicationCheckingReport = applicationCheckingReportRepository.findByApplicationInfoIdAndReportType(applicationId, reportsType);

        return applicationCheckingReport.isPresent() ? applicationCheckingReport.get() :null;


    }

    @Override
    public ApplicationCheckingReport findBySupportServicesTypeIdAndReportType(Long serviceId, ReportsType reportsType) {
        Optional<ApplicationCheckingReport> applicationCheckingReport = applicationCheckingReportRepository.findBySupportServicesTypeIdAndReportType(serviceId, reportsType);
        return applicationCheckingReport.isPresent() ? applicationCheckingReport.get() : null;

    }

    @Override
    @Transactional
    public ApplicationCheckingReport insert(ApplicationCheckingReport entity) {
        return super.insert(entity);
    }
}
