package gov.saip.applicationservice.common.controllers.patent;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.service.installment.impl.ApplicationInstallmentJobs;
import gov.saip.applicationservice.common.service.protectionElementsMigration.ProtectionElementMigrationTask;
import gov.saip.applicationservice.scheduler.FailedGeneratedPdfDocumentScheduler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("pb/protectionElements")
public class ProtectionElementMigrationController {


    @Autowired
    private ProtectionElementMigrationTask protectionElementMigrationTask;

    @Autowired
    FailedGeneratedPdfDocumentScheduler failedGeneratedPdfDocumentScheduler;

    @Autowired
      ApplicationInstallmentJobs applicationInstallmentJobs;



    @GetMapping("/startMigration")
    public ApiResponse<Integer> getProtectionElements(){
       // protectionElementMigrationTask.start();
        return ApiResponse.ok( 200);
    }


    @GetMapping("/startretryGeneratingAllFailedPdfDocument")
    public ApiResponse<Integer> retryGeneratingAllFailedPdfDocument(){
        failedGeneratedPdfDocumentScheduler.generateDocumentForAllPatentApplicationsWithNoDocument();
        return ApiResponse.ok( 200);
    }


    @GetMapping("/startretryreGeneratingAllFailedPdfDocument")
    public ApiResponse<Integer> retryreGeneratingAllFailedPdfDocument(){
        failedGeneratedPdfDocumentScheduler.regenerateAllFailedDocuments();
        return ApiResponse.ok( 200);
    }


    @GetMapping("/startapplicationInstallmentJobs")
    public ApiResponse<Integer> retryapplicationInstallmentJobs(){
        applicationInstallmentJobs. processApplicationCategoryInstallment();
        return ApiResponse.ok( 200);
    }



}
