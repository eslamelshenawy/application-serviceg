package gov.saip.applicationservice.common.controllers.ApplicantModifications;


import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.applicantModifications.TradeMarkApplicationModificationDto;
import gov.saip.applicationservice.common.model.TrademarkApplicationModification;
import gov.saip.applicationservice.common.model.trademark.TrademarkDetail;
import gov.saip.applicationservice.common.service.applicantModifications.TradeMarkApplicationModificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping(value = {"/kc/trademark-application-modification", "/internal-calling/trademark-application-modification"})
@RequiredArgsConstructor
@Slf4j
public class TradeMarkApplicationModificationController {

    private final TradeMarkApplicationModificationService applicantModificationsService;

    @PutMapping("/{appId}")
    public ApiResponse<Long> updateApplicantTradMarkModifications(@PathVariable(name="appId") String appId, @RequestBody TradeMarkApplicationModificationDto dto){
        TrademarkApplicationModification modificationTracking =applicantModificationsService.addApplicantModifications(dto,Long.valueOf(appId));
        return ApiResponse.created(modificationTracking.getId());
    }

    @GetMapping("/application/{appId}")
    public ApiResponse<Map<String,Object>> ListAllApplicationTradeMarkModifications(@PathVariable(name="appId") String appId){
       return ApiResponse.ok(applicantModificationsService.getAuditModificationsDifferences(Long.valueOf(appId)));
    }
    @PutMapping("/apply/application/{appId}")
    public ApiResponse<String> persistCheckerChanges(@PathVariable(name="appId") String appId){
        applicantModificationsService.persistNewTradeMarkModificationsAndCompleteApplicantTask(Long.valueOf(appId));
       return ApiResponse.ok("successfully apply changes");
    }


	

}
