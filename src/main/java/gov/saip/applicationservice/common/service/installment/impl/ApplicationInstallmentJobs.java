package gov.saip.applicationservice.common.service.installment.impl;

import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.ApplicationStatusEnum;
import gov.saip.applicationservice.common.enums.installment.InstallmentStatus;
import gov.saip.applicationservice.common.enums.installment.InstallmentType;
import gov.saip.applicationservice.common.facade.publication.support_service.SupportServicePublicationIssueFacade;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import gov.saip.applicationservice.common.repository.installment.ApplicationInstallmentConfigRepository;
import gov.saip.applicationservice.common.repository.installment.ApplicationInstallmentRepository;
import gov.saip.applicationservice.common.service.installment.processor.InstallmentDesertionProcessor;
import gov.saip.applicationservice.common.service.installment.processor.InstallmentDueProcessor;
import gov.saip.applicationservice.common.service.installment.processor.InstallmentOverDueProcessor;
import gov.saip.applicationservice.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.core.SchedulerLock;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ApplicationInstallmentJobs {
    private final ApplicationInstallmentConfigRepository installmentConfigRepository;
    private final ApplicationInstallmentRepository applicationInstallmentRepository;

    private final InstallmentDueProcessor installmentDueProcessor;
    private final InstallmentOverDueProcessor installmentOverDueProcessor;
    private final InstallmentDesertionProcessor installmentDesertionProcessor;
    private final SupportServicePublicationIssueFacade supportServicePublicationIssueFacade;

     @Scheduled(cron = "${scheduler.installment.cron}")
//     @Scheduled(cron="#{@getInstallmentJobExpressionBean}")
//     @Scheduled(cron="0 */5 * ? * *")
     @SchedulerLock(lockAtLeastForString = "${scheduler.installment.shed-lock-least}", lockAtMostForString = "${scheduler.installment.shed-lock-most}", name = "ApplicationInstallmentJobs_processApplicationCategoryInstallment")
    public void processApplicationCategoryInstallment() {
         log.info("INSTALLMENT_JOB: **installment cron job has been started**");

        List<ApplicationInstallmentConfig> configs = installmentConfigRepository.findAll();
        if (configs.isEmpty())
            throw new BusinessException("");

        for (ApplicationInstallmentConfig config : configs) {
            processApplicationCategoryInstallment(config);
        }

         installmentDesertionProcessor.flushCacheAfterProcessing();
         log.info("INSTALLMENT_JOB: **installment cron job has been finished**");
    }


    public void processApplicationCategoryInstallment(ApplicationInstallmentConfig config) {
        log.info("INSTALLMENT_JOB: we have started to process ==> {}-{} type", config.getApplicationCategory().name(), config.getInstallmentType());
        try {
            log.info("INSTALLMENT_JOB: start process ==> {}-{}-Due",  config.getApplicationCategory().name(), config.getInstallmentType());
            installmentDueProcessor.process(config);
            log.info("INSTALLMENT_JOB: end process ==> {}-{}-Due",  config.getApplicationCategory().name(), config.getInstallmentType());
        } catch(Exception ex) {
            log.error("INSTALLMENT_JOB: exception while processing over due today installments");
            log.error(ex.getMessage());
            //log exception on db
        }

        try {
            log.info("INSTALLMENT_JOB: start process ==> {}-{}-Due Over",  config.getApplicationCategory().name(), config.getInstallmentType());
            installmentOverDueProcessor.process(config);
            log.info("INSTALLMENT_JOB: end process ==> {}-{}-Due Over",  config.getApplicationCategory().name(), config.getInstallmentType());
        } catch(Exception ex) {
            log.error("INSTALLMENT_JOB: exception while processing due today installments");
            log.error(ex.getMessage());
            //log exception on db
        }

        try {
            log.info("INSTALLMENT_JOB: start process ==> {}-{}-Desertion",  config.getApplicationCategory().name(), config.getInstallmentType());
            installmentDesertionProcessor.process(config);
            log.info("INSTALLMENT_JOB: end process ==> {}-{}-Desertion",  config.getApplicationCategory().name(), config.getInstallmentType());
        } catch(Exception ex) {
            log.error("INSTALLMENT_JOB: exception while processing new installments");
            log.error(ex.getMessage());
            //log exception on db
        }

        installmentConfigRepository.updateLastRunningDate(config.getApplicationCategory(), LocalDateTime.now());
        log.info("INSTALLMENT_JOB: we have finished process ==> {}-{} type", config.getApplicationCategory().name(), config.getInstallmentType());

        postponedAnnualFeesForNotApprovedAndPaidThreeYears();
     }



    // every year on 31-12 at 5 PM postponed not accepted apps, to prevent create bill next year
   // @SchedulerLock(lockAtLeastForString = "${scheduler.installment.shed-lock-least}", lockAtMostForString = "${scheduler.installment.shed-lock-most}", name = "ApplicationInstallmentJobs_postponedAnnualFeesForNotAcceptedApplicationsAndPaidThreeYears")
    public void postponedAnnualFeesForNotApprovedAndPaidThreeYears() {
        log.info("start to postponed annual fees that is not accepted after payment for 3 years ");
        applicationInstallmentRepository.postponedAnnualFeesForNotAcceptedApplicationsAndPaidThreeYears(List.of(ApplicationStatusEnum.ACCEPTANCE.name()), List.of(ApplicationCategoryEnum.PATENT.name()), InstallmentType.ANNUAL, 3L, InstallmentStatus.NEW, InstallmentStatus.POSTPONED);
        log.info("end to postponed annual fees that is not accepted after payment for 3 years ");
     }

}
