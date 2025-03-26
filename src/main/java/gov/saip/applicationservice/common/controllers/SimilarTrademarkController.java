package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.SimilarTrademarkDto;
import gov.saip.applicationservice.common.mapper.SimilarTrademarkMapper;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.SimilarTrademark;
import gov.saip.applicationservice.common.service.SimilarTrademarkService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/similar-trademark", "/internal-calling/similar-trademark"})
@RequiredArgsConstructor
@Slf4j
public class SimilarTrademarkController extends BaseController<SimilarTrademark, SimilarTrademarkDto, Long> {
    private final SimilarTrademarkService similarTrademarkService;
    private final SimilarTrademarkMapper similarTrademarkMapper;

    @Override
    protected BaseService<SimilarTrademark, Long> getService() {
        return  similarTrademarkService;
    }

    @Override
    protected BaseMapper<SimilarTrademark, SimilarTrademarkDto> getMapper() {
        return similarTrademarkMapper;
    }
    @DeleteMapping("/delete/{id}")
    public ApiResponse<Integer> deleteById(@PathVariable Long id) {
        return ApiResponse.ok(similarTrademarkService.softDelete(id));
    }
    @GetMapping("/applicationId/{applicationId}")
    public ApiResponse<List<SimilarTrademarkDto>> findByApplicationId(
            @PathVariable Long applicationId) {
        return ApiResponse.ok(similarTrademarkService.findByApplicationInfoId(applicationId));
    }

    @GetMapping("/task/{taskInstanceId}")
    public ApiResponse<List<SimilarTrademarkDto>> getByTaskInstanceId(
            @PathVariable("taskInstanceId") String taskInstanceId) {
        return ApiResponse.ok(similarTrademarkService.getTaskInstanceId(taskInstanceId));
    }


}
