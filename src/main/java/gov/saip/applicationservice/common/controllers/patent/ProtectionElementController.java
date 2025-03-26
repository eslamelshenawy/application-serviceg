package gov.saip.applicationservice.common.controllers.patent;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ProtectionElementCounts;
import gov.saip.applicationservice.common.dto.patent.ProjectionElementsRequestDTO;
import gov.saip.applicationservice.common.dto.patent.ProjectionElementsResponseDTO;
import gov.saip.applicationservice.common.service.patent.ProtectionElementService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping({"kc/protection-elements","internal-calling/protection-elements","pb/protection-elements"})
@RequiredArgsConstructor
public class ProtectionElementController {
    private final ProtectionElementService protectionElementService;
    @GetMapping("/{id}")
    public ApiResponse<List<ProjectionElementsResponseDTO>> getProtectionElements(@PathVariable("id") Long applicationId, @RequestParam("isEnglish")Boolean isEnglish){
        return ApiResponse.ok(protectionElementService.getProtectionElements(applicationId, isEnglish));
    }
 @GetMapping("/application/{appId}")
    public ApiResponse<List<ProjectionElementsResponseDTO>> getProtectionElementsByApplicationId(@PathVariable("appId") Long applicationId){
        return ApiResponse.ok(protectionElementService.getProtectionElementsByApplicationId(applicationId));
    }

    @PostMapping
    public ApiResponse<Void> addProtectionElements(@RequestBody ProjectionElementsRequestDTO projectionElementsRequestDTO){
        protectionElementService.addProtectionElements(projectionElementsRequestDTO);

        return ApiResponse.ok();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteProtectionElement(@PathVariable("id") Long id){
        protectionElementService.deleteProtectionElements(id);
        return ApiResponse.ok();
    }

    @GetMapping("/parent-protection-element-numbers/{appId}")
    public ApiResponse<Long> getCountParentProtectionElementsByApplicationId(@PathVariable("appId") Long applicationId){
        return ApiResponse.ok(protectionElementService.getCountParentProtectionElementsByApplicationId(applicationId));
    }
    @GetMapping("/children-protection-element-numbers/{appId}")
    public ApiResponse<Long> getCountChildrenProtectionElementsByApplicationId(@PathVariable("appId") Long applicationId){
        return ApiResponse.ok(protectionElementService.getCountChildrenProtectionElementsByApplicationId(applicationId));
    }
    @GetMapping("/subsuntitive-examination-fees/{appId}")
    public ApiResponse<ProtectionElementCounts> getProtectionElementCountsByApplicationId(@PathVariable("appId") Long applicationId ){
        return ApiResponse.ok(protectionElementService.getProtectionElementCountsByApplicationId(applicationId));
    }
}
