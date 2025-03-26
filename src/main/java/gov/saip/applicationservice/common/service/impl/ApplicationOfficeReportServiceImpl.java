package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.ApplicationOfficeReport;
import gov.saip.applicationservice.common.repository.ApplicationOfficeReportRepository;
import gov.saip.applicationservice.common.service.ApplicationOfficeReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional
public class ApplicationOfficeReportServiceImpl extends BaseServiceImpl<ApplicationOfficeReport, Long> implements ApplicationOfficeReportService {

    private final ApplicationOfficeReportRepository applicationOfficeReportRepository;
    @Override
    protected BaseRepository<ApplicationOfficeReport, Long> getRepository() {
        return applicationOfficeReportRepository;
    }
    @Override
    public List<ApplicationOfficeReport> getAllByApplicationId(Long appId) {
        return applicationOfficeReportRepository.findByApplicationInfoId(appId);
    }
}
