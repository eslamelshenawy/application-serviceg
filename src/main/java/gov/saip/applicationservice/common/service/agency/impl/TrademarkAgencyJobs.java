package gov.saip.applicationservice.common.service.agency.impl;

import gov.saip.applicationservice.common.clients.rest.feigns.BPMMigrationCallerFeignClient;
import gov.saip.applicationservice.common.dto.requests.ProcessInstanceRequestDto;
import gov.saip.applicationservice.common.service.agency.TrademarkAgencyRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrademarkAgencyJobs {
    
    private final TrademarkAgencyRequestService trademarkAgencyRequest;
    private final BPMMigrationCallerFeignClient bpmMigrationCallerFeignClient;
    
    @Scheduled(cron = "${scheduler.expired_agency.cron}")
    public void processApplicationCategoryInstallment() {
        try {
            log.info("EXPIRED_AGENCY_JOB: **expired_agency cron job has been started**");
            List<Long> processRequestIds = trademarkAgencyRequest.getExpiredTrademarkAgencyProcessRequestIds();
            trademarkAgencyRequest.updateTrademarkAgencyToExpiredStatus();
            bpmMigrationCallerFeignClient.deleteBulkOfProcesses(ProcessInstanceRequestDto.builder().processesRequestsIds(processRequestIds).build());
            log.info("EXPIRED_AGENCY_JOB: **expired_agency cron job has been finished**");
        } catch (Exception exception) {
            log.error("EXPIRED_AGENCY_JOB_EXCEPTION: **expired_agency cron job has exception {} **", exception.getMessage());
        }
    }
    
    
}
