package gov.saip.applicationservice.common.controllers.annualfees;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.annualfees.AnnualFeesRequestDto;
import gov.saip.applicationservice.common.mapper.annualfees.AnnualFeesMapper;
import gov.saip.applicationservice.common.model.annual_fees.AnnualFeesRequest;
import gov.saip.applicationservice.common.service.annualfees.AnnualFeesService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = {"/kc/annual-fees", "/internal-calling/annual-fees"})
@RequiredArgsConstructor
public class AnnualFeesRequestController extends BaseController<AnnualFeesRequest, AnnualFeesRequestDto, Long> {

    private final AnnualFeesService annualFeesService;
    private final AnnualFeesMapper annualFeesMapper;
    @Override
    protected BaseService<AnnualFeesRequest, Long> getService() {
        return annualFeesService;
    }

    @Override
    protected BaseMapper<AnnualFeesRequest, AnnualFeesRequestDto> getMapper() {
        return annualFeesMapper;
    }

    @GetMapping("/service/{id}")
    public ApiResponse<AnnualFeesRequestDto> getAnnualFeesByApplicationSupportServiceId(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(annualFeesMapper.map(annualFeesService.findById(id)));
    }
}
