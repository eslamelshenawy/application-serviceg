package gov.saip.applicationservice.common.controllers.supportService;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.clients.rest.feigns.CustomerClient;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.supportService.ApplicationPriorityModifyRequestDetailsDto;
import gov.saip.applicationservice.common.dto.supportService.ApplicationPriorityModifyRequestDto;
import gov.saip.applicationservice.common.dto.supportService.ApplicationPriorityModifyRequestLightDto;
import gov.saip.applicationservice.common.mapper.supportService.ApplicationPriorityModifyRequestMapper;
import gov.saip.applicationservice.common.model.supportService.ApplicationPriorityModifyRequest;
import gov.saip.applicationservice.common.service.supportService.ApplicationPriorityModifyRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping({"/kc/support-service/priority-modify-request", "/internal-calling/support-service/priority-modify-request"})
@RequiredArgsConstructor
public class ApplicationPriorityModifyRequestController extends BaseController<ApplicationPriorityModifyRequest, ApplicationPriorityModifyRequestDto, Long> {
    
    private final ApplicationPriorityModifyRequestService applicationPriorityModifyRequestService;
    private final ApplicationPriorityModifyRequestMapper applicationPriorityModifyRequestMapper;
    private final CustomerClient customerClient;
    @Override
    protected BaseService<ApplicationPriorityModifyRequest, Long> getService() {
        return applicationPriorityModifyRequestService;
    }

    @Override
    protected BaseMapper<ApplicationPriorityModifyRequest, ApplicationPriorityModifyRequestDto> getMapper() {
        return applicationPriorityModifyRequestMapper;
    }

    @Override
    @GetMapping("/{id}")
    public ApiResponse<ApplicationPriorityModifyRequestDto> findById(@PathVariable(name = "id") Long id) {
        ApiResponse<ApplicationPriorityModifyRequestDto> requestDto = super.findById(id);
        for (ApplicationPriorityModifyRequestDetailsDto requestDetailsDto : requestDto.getPayload().getApplicationPriorityDtoList()) {
            requestDetailsDto.setCountry(customerClient.getCountryById(requestDetailsDto.getCountryId()).getPayload());
        }
        return requestDto;
    }

    @PutMapping("/update-application-priority")
    public void updateApplicationPriority(@RequestBody ApplicationPriorityModifyRequestLightDto dto){
        applicationPriorityModifyRequestService.updateApplicationPriority(dto);
    }
}