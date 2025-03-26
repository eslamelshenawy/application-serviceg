package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.TermsAndConditionsDto;
import gov.saip.applicationservice.common.service.TermsAndConditionsService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/kc/termsAndConditions")
public class TermsAndConditionsController {

    private final TermsAndConditionsService termsAndConditionsService;

    public TermsAndConditionsController(TermsAndConditionsService termsAndConditionsService) {
        this.termsAndConditionsService = termsAndConditionsService;
    }

    @GetMapping("")
    public List<TermsAndConditionsDto> getAllTermsAndConditions() {
        return termsAndConditionsService.getAllTermsAndConditionsSorted();
    }

    @GetMapping("/app-category/{appCategory}/request-type/{requestType}")
    public List<TermsAndConditionsDto> getTermsAndConditions(@PathVariable String appCategory, @PathVariable String requestType) {
        return termsAndConditionsService.getTermsAndConditionsSorted(appCategory , requestType);
    }

}

