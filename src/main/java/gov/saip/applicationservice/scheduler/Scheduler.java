package gov.saip.applicationservice.scheduler;

import com.savoirtech.logging.slf4j.json.LoggerFactory;
import com.savoirtech.logging.slf4j.json.logger.Logger;
import gov.saip.applicationservice.common.service.ApplicationPriorityService;
import gov.saip.applicationservice.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class Scheduler {

    private static final Logger logger = LoggerFactory.getLogger(Scheduler.class);
    private final ApplicationPriorityService applicationPriorityService;

    @Scheduled(cron = "${scheduler.priority.cron}") // every 1 hour
    public void priorityScheduler() {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            Date now = new Date();
            String strDate = sdf.format(now);
            applicationPriorityService.setExpiredAndSendNotifiction();
            applicationPriorityService.setAboutExpiredAndSendNotifiction();
            logger.info().message("priorityScheduler " + strDate).log();

        } catch (BusinessException businessException) {
            logger.error().message(businessException.toString()).log();
            throw businessException;
        } catch (Exception exception) {
            logger.error().exception("exception", exception).message(exception.getMessage()).log();
            throw exception;
        }
    }
}
