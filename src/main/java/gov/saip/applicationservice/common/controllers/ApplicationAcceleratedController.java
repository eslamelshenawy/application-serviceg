package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationAcceleratedDto;
import gov.saip.applicationservice.common.service.ApplicationAcceleratedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/kc/applicationAccelerated")
public class ApplicationAcceleratedController {

    @Autowired
    private ApplicationAcceleratedService applicationAcceleratedService;

    @PostMapping("")
    public ApiResponse<Long> addApplicationAccelerated(@RequestBody ApplicationAcceleratedDto applicationAcceleratedDto) {
        return ApiResponse.ok(applicationAcceleratedService.addOrUpdateApplicationAccelerated(applicationAcceleratedDto));
    }

    @DeleteMapping("/{id}/delete-doc")
    public ApiResponse<Long> nullifyField(@PathVariable Long id, @RequestParam String fieldKey) {
        return ApiResponse.ok(applicationAcceleratedService.deleteApplicationAcceleratedFile(id, fieldKey));
    }
    
    @DeleteMapping("/application/{id}/hard-deleted")
    public ApiResponse<?> deleteByApplicationId(@PathVariable Long id) {
        applicationAcceleratedService.deleteByApplicationId(id);
        return ApiResponse.ok(null);
    }

}
