package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationSubClassificationDto;
import gov.saip.applicationservice.common.dto.SubClassificationDto;
import gov.saip.applicationservice.common.service.ApplicationSubClassificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value= {"kc/application-sub-classification", "/internal-calling/application-sub-classification","/pb/application-sub-classification"})
@RequiredArgsConstructor
public class ApplicationSubClassificationController {

    private final ApplicationSubClassificationService subClassificationService;

    @PostMapping("/application/{id}")
    public ApiResponse<Long> create(@Valid @RequestBody ApplicationSubClassificationDto req, @PathVariable Long id) {
        return ApiResponse.created(subClassificationService.createApplicationSubClassification(req, id));
    }

    @PutMapping("/application/{id}")
    public ApiResponse<Long> update(@Valid @RequestBody ApplicationSubClassificationDto req, @PathVariable Long id) {
        return ApiResponse.created(subClassificationService.updateApplicationSubClassification(req, id));
    }


    @DeleteMapping("/application/{appId}/classification/{classId}")
    public ApiResponse<Void> deleteByAppIdAndClassId(@PathVariable("appId") Long appId, @PathVariable("classId") Long classId) {
        subClassificationService.deleteByAppIdAndClassId(appId, classId);
        return ApiResponse.noContent();
    }


    @GetMapping("/application/{id}")
    public ApiResponse<List<SubClassificationDto>> list(@PathVariable(name = "id") Long id,
                                                        @RequestParam(required=false, defaultValue ="0") int page,
                                                        @RequestParam(required = false, defaultValue = "10") int limit) {
        return ApiResponse.ok(subClassificationService.listApplicationSubClassifications(id,page,limit));
    }


}
