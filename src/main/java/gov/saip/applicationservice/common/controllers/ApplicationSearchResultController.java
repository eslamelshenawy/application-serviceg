package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationSearchResultDto;
import gov.saip.applicationservice.common.mapper.ApplicationSearchResultMapper;
import gov.saip.applicationservice.common.model.ApplicationSearchResult;
import gov.saip.applicationservice.common.service.ApplicationSearchResultService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController()
@RequestMapping(value = {"/kc/search-result", "/internal-calling/search-result"})
@RequiredArgsConstructor
public class ApplicationSearchResultController extends BaseController<ApplicationSearchResult, ApplicationSearchResultDto, Long> {

    private final ApplicationSearchResultService applicationSearchResultService;
    private final ApplicationSearchResultMapper applicationSearchResultMapper;
    @Override
    protected BaseService<ApplicationSearchResult, Long> getService() {
        return applicationSearchResultService;
    }

    @Override
    protected BaseMapper<ApplicationSearchResult, ApplicationSearchResultDto> getMapper() {
        return applicationSearchResultMapper;
    }
    @GetMapping("/{appId}/application")
    public ApiResponse<List<ApplicationSearchResultDto>> getAllSearchResults(@PathVariable(name = "appId") Long appId) {
        return ApiResponse.ok(applicationSearchResultMapper.map(applicationSearchResultService.getAllSearchResultsByApplicationId(appId)));
    }

}
