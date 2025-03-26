package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.clients.rest.feigns.CustomerClient;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.service.ApplicationInfoService;
import gov.saip.applicationservice.common.service.ApplicationPriorityService;
import gov.saip.applicationservice.common.validators.PrioritiesValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value={"/kc/applicationpriorities","/internal-calling"})
public class ApplicationPriorityController {

    private final ApplicationPriorityService applicationPriorityService;
    private final PrioritiesValidator prioritiesValidator;
    private final CustomerClient customerClient;
    private final ApplicationInfoService applicationInfoService;
    @Autowired
    public ApplicationPriorityController(ApplicationPriorityService applicationPriorityService, CustomerClient customerClient,
                                         PrioritiesValidator prioritiesValidator,ApplicationInfoService applicationInfoService) {
        this.applicationPriorityService = applicationPriorityService;
        this.customerClient = customerClient;
        this.prioritiesValidator = prioritiesValidator;
        this.applicationInfoService = applicationInfoService;
    }

    @PostMapping(value = "/{applicationInfoId}")
    public ApiResponse<Long> createUpdateApplicationPriority(@Valid @RequestBody ApplicationPriorityDto applicationPriorityDto, @PathVariable Long applicationInfoId) {
        applicationPriorityDto.setApplicationId(applicationInfoId);
        prioritiesValidator.validate(applicationPriorityDto,null);
        return ApiResponse.ok(applicationPriorityService.createUpdateApplicationPriority(applicationPriorityDto, applicationInfoId));
    }
    @PutMapping (value = "/update")
    public ApiResponse<Long> createUpdateApplicationPriority(@Valid @RequestBody ApplicationPriorityDto applicationPriorityDto) {
        applicationPriorityDto.setApplicationId(applicationPriorityDto.getApplicationId());
        prioritiesValidator.validate(applicationPriorityDto,null);
        return ApiResponse.ok(applicationPriorityService.createUpdateApplicationPriority(applicationPriorityDto, applicationPriorityDto.getApplicationId()));
    }

    @PostMapping(value = "/{applicationInfoId}/bulk")
    public ApiResponse<List<Long>> createUpdateApplicationPriority(@Valid @RequestBody ApplicationPriorityBulkDto applicationPriorityBulkDto, @PathVariable Long applicationInfoId) {
        return ApiResponse.ok(applicationPriorityService.createUpdateApplicationPriority(applicationPriorityBulkDto, applicationInfoId));
    }


    @DeleteMapping("/{id}/delete-doc")
    public ApiResponse<Long> nullifyField(@PathVariable Long id, @RequestParam String fieldKey) {
        return ApiResponse.ok(applicationPriorityService.deleteApplicationPriorityFile(id, fieldKey));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Long> deleteApplicationPriority(@PathVariable Long id) {
        return ApiResponse.ok(applicationPriorityService.deleteApplicationPriority(id));
    }

    @PatchMapping("/{id}/update-priority-docs")
    public ApiResponse<Long> updatePriorityDocs(@Valid @RequestBody ApplicationPriorityDocsDto applicationPriorityDocsDto
            , @PathVariable Long id) {
        return ApiResponse.ok(applicationPriorityService.updatePriorityDocs(id, applicationPriorityDocsDto));
    }

    @PatchMapping("/{id}/update-priority-docs-complete")
    public ApiResponse<Long> updatePriorityDocsWithCompleteTask(@Valid @RequestBody ApplicationPriorityDocsDto applicationPriorityDocsDto
            , @PathVariable Long id) {
        return ApiResponse.ok(applicationPriorityService.updatePriorityDocsAndCompleteTask(id, applicationPriorityDocsDto));
    }

    @GetMapping("/priority_documents_allowance_days")
    public ApiResponse<Long> getPriorityDocumentsAllowanceDays() {
        return ApiResponse.ok(applicationPriorityService.getPriorityDocumentsAllowanceDays());
    }

    @GetMapping("/application/{appId}")
    public ApiResponse<List<ApplicationPriorityListDto>> getApplicationPriorites(@PathVariable(name="appId") Long appId) {
        List<ApplicationPriorityListDto> dtosList = applicationPriorityService.listApplicationPriorites(appId);
        for (ApplicationPriorityListDto dto : dtosList) {
             dto.setCountry(customerClient.getCountryById(dto.getCountryId()).getPayload());
        }
        return ApiResponse.ok(dtosList);
    }

    @PutMapping("/{id}/update-priority-status-comment")
    public ApiResponse<Long> updatePriorityDocs( @RequestBody ApplicationPriorityUpdateStatusAndCommentsDto updateStatuscommentDto
            , @PathVariable Long id) {
        return ApiResponse.ok(applicationPriorityService.updatePriorityStatusAndComment(id,updateStatuscommentDto));
    }

    @PostMapping(value = "/{applicationInfoId}/with-complete")
    public ApiResponse<Long> createUpdateApplicationPriorityWithComplete(@Valid @RequestBody ApplicationPriorityDto applicationPriorityDto, @PathVariable Long applicationInfoId) {
        return ApiResponse.ok(applicationPriorityService.createUpdateApplicationPriorityWithComplete(applicationPriorityDto, applicationInfoId));
    }



    @GetMapping(value = "/no-priority-document/{appId}")
    public ApiResponse<List<ApplicationPriorityListDto>> getPrioritiesThatHaveNotPriorityDocument(@PathVariable("appId") Long appId) {
        return ApiResponse.ok(applicationPriorityService.getPrioritiesThatHaveNotPriorityDocument(appId));
    }


    @GetMapping("/get-priority/{priorityId}")
    public ApiResponse<ApplicationPriorityListDto> getApplicationPriorityById(@PathVariable("priorityId")Long priorityId){
        return ApiResponse.ok(applicationPriorityService.getApplicationPriorityById(priorityId));
    }


    @GetMapping("/complete-priority-task/{appId}")
    public ApiResponse<Void> completeApplicantPriorityDocument(@PathVariable("appId") Long appId ,@RequestParam(name = "categoryCode",required = false) String categoryCode){
        applicationPriorityService.completeApplicantPriorityDocument(appId,categoryCode);
        return ApiResponse.ok();
    }

    @GetMapping("/bill-application-attributes/{appId}")
    public ApiResponse<BillApplicationInfoAttributesDto> getBillApplicationAttributes(@PathVariable(name = "appId") Long appId) {
       return ApiResponse.ok(applicationInfoService.getBillApplicationAttributes(appId));
    }
}
