package gov.saip.applicationservice.common.controllers.annualfees;


import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.annualfees.LkAnnualRequestYearsDto;
import gov.saip.applicationservice.common.model.annual_fees.LkAnnualRequestYears;
import gov.saip.applicationservice.common.service.annualfees.LkAnnualRequestYearsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"kc/annual-fees/years" , "internal-calling/annual-fees/years"})
@AllArgsConstructor
public class LkAnnualRequestYearsController extends BaseLkpController <LkAnnualRequestYears, LkAnnualRequestYearsDto, Long>  {

    private final LkAnnualRequestYearsService lkAnnualRequestYearsService;

    @GetMapping("/application/{id}")
    public ApiResponse<List<LkAnnualRequestYearsDto>> getAnnualYearsByAppId(@PathVariable("id") Long id) {
        return ApiResponse.ok(lkAnnualRequestYearsService.getAnnualYearsByAppId(id));
    }
}
