package gov.saip.applicationservice.common.controllers.opposition;


import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.opposition.OppositionDetailsProjection;
import gov.saip.applicationservice.common.dto.opposition.OppositionRequestDto;
import gov.saip.applicationservice.common.mapper.opposition.OppositionRequestMapper;
import gov.saip.applicationservice.common.model.opposition.OppositionRequest;
import gov.saip.applicationservice.common.service.opposition.OppositionRequestService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

import static gov.saip.applicationservice.common.enums.SupportServiceType.VOLUNTARY_REVOKE;

@RequiredArgsConstructor
@Slf4j
@RestController
@RequestMapping({"/kc/support-service/opposition-request","/internal-calling/support-service/opposition-request"})
public class OppositionRequestController extends BaseController<OppositionRequest, OppositionRequestDto, Long> {


    private final OppositionRequestMapper oppositionRequestMapper;
    private final OppositionRequestService oppositionRequestService;
    @Override
    protected BaseService<OppositionRequest, Long> getService() {
        return oppositionRequestService;
    }

    @Override
    protected BaseMapper<OppositionRequest, OppositionRequestDto> getMapper() {
        return oppositionRequestMapper;
    }

    @GetMapping("/{appId}/application")
    public ApiResponse<List<OppositionRequestDto>> getAllReports(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(oppositionRequestMapper.map(oppositionRequestService.getAllByApplicationId(appId, VOLUNTARY_REVOKE)));
    }

    @GetMapping("/service/{id}")
    public ApiResponse<OppositionRequestDto> getOppositionRequestByApplicationSupportServiceId(@PathVariable(name = "id") Long applicationSupportServiceId) {
        return ApiResponse.ok(oppositionRequestService.getRequestById(applicationSupportServiceId));
    }

    @PutMapping("/update/complainer-opposition")
    public ApiResponse<Long> updateComplainerOppositionRequest(@RequestBody OppositionRequestDto oppositionRequestDto){
        OppositionRequest oppositionRequest = oppositionRequestMapper.unMap(oppositionRequestDto);
        return ApiResponse.ok(oppositionRequestService.updateComplainerOpposition(oppositionRequest));
    }

    @PutMapping("/add/application-owner-reply")
    public ApiResponse<Long> addApplicationOwnerReply(@RequestBody OppositionRequestDto oppositionRequestDto){
        OppositionRequest oppositionRequest = oppositionRequestMapper.unMap(oppositionRequestDto);
        return ApiResponse.ok(oppositionRequestService.addApplicationOwnerReply(oppositionRequest));
    }

    @GetMapping("/details")
    public ApiResponse<PaginationDto<List<OppositionDetailsProjection>>> getRequestsDetails(
            @RequestParam(required = false, value = "page", defaultValue = "0") Integer page,
            @RequestParam(required = false, value = "limit", defaultValue = "10") Integer limit,
            @RequestParam(required = false) String requestNumber,
            @RequestParam(required = false ) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)LocalDate filteredDate,
            @RequestParam(value = "from", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam(value = "to", required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ){
        return ApiResponse.ok(oppositionRequestService.getRequestsDetails(requestNumber, filteredDate, from, to, limit, page));
    }


    @PutMapping("/update/complainer-hearing-session")
    public ApiResponse<Long> updateComplainerHearingSession(@RequestBody OppositionRequestDto oppositionRequestDto){
        OppositionRequest oppositionRequest = oppositionRequestMapper.unMap(oppositionRequestDto);
        return ApiResponse.ok(oppositionRequestService.updateComplainerHearingSession(oppositionRequest));
    }

    @PutMapping("/update/owner-hearing-session")
    public ApiResponse<Long> updateApplicationOwnerHearingSession(@RequestBody OppositionRequestDto oppositionRequestDto){
        OppositionRequest oppositionRequest = oppositionRequestMapper.unMap(oppositionRequestDto);
        return ApiResponse.ok(oppositionRequestService.updateApplicationOwnerHearingSession(oppositionRequest));
    }

    @PutMapping("/add/complainer-session-result")
    public ApiResponse<Long> addComplainerHearingSessionResult(@RequestBody OppositionRequestDto oppositionRequestDto){
        OppositionRequest oppositionRequest = oppositionRequestMapper.unMap(oppositionRequestDto);
        return ApiResponse.ok(oppositionRequestService.addComplainerHearingSessionResult(oppositionRequest));
    }

    @PutMapping("/add/owner-session-result")
    public ApiResponse<Long> addApplicationOwnerHearingSessionResult(@RequestBody OppositionRequestDto oppositionRequestDto){
        OppositionRequest oppositionRequest = oppositionRequestMapper.unMap(oppositionRequestDto);
        return ApiResponse.ok(oppositionRequestService.addApplicationOwnerHearingSessionResult(oppositionRequest));
    }

    @GetMapping("/opposition-due-date/{appId}")
    public ApiResponse<LocalDate> maxDueDateOfOpposition(@PathVariable(name = "appId") Long applicationId){
           return ApiResponse.ok(oppositionRequestService.getMaxDateOfOpposition(applicationId));
    }
    @PutMapping("/{id}/update-app-status")
    public ApiResponse<Void> changeOppositionRequestStatusAfterFinalDecision(@PathVariable(name = "id") Long id) {
        oppositionRequestService.changeOppositionRequestStatusAfterFinalDecision(id);
        return ApiResponse.noContent();
    }



}
