package gov.saip.applicationservice.common.controllers;


import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationSearchDto;
import gov.saip.applicationservice.common.dto.ApplicationSearchSimilarsDto;
import gov.saip.applicationservice.common.mapper.ApplicationSearchMapper;
import gov.saip.applicationservice.common.model.ApplicationSearch;
import gov.saip.applicationservice.common.service.ApplicationSearchService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping({"/kc/support-service/application-search","/internal-calling/support-service/application-search"})
public class ApplicationSearchController extends BaseController<ApplicationSearch , ApplicationSearchDto , Long> {


    private final ApplicationSearchMapper applicationSearchMapper;
    private final ApplicationSearchService applicationSearchService;
    @Override
    protected BaseService<ApplicationSearch, Long> getService() {
        return applicationSearchService;
    }

    @Override
    protected BaseMapper<ApplicationSearch, ApplicationSearchDto> getMapper() {
        return applicationSearchMapper;
    }
    @GetMapping("/service/{id}")
    public ApiResponse<ApplicationSearchDto> getApplicationSearchByApplicationSupportServiceId(@PathVariable(name = "id") Long supportServiceId) {
        return ApiResponse.ok(applicationSearchMapper.map(applicationSearchService.findById(supportServiceId)));
    }



    @GetMapping("/sync/{applicationSearchId}")
    public ApiResponse<List<ApplicationSearchSimilarsDto>> getApplicationsByApplicationSearchId(@PathVariable(name = "applicationSearchId") Long applicationSearchId) {
        return ApiResponse.ok(applicationSearchService.fetchSimilarApplicationsFromIpSearch(applicationSearchId));
    }


    @GetMapping("/similars/{applicationSearchId}")
    public ApiResponse<List<ApplicationSearchSimilarsDto>> getSavedApplicationSimilars(@PathVariable(name = "applicationSearchId") Long applicationSearchId){
        return ApiResponse.ok(applicationSearchService.getSavedApplicationSimilars(applicationSearchId));
    }




}
