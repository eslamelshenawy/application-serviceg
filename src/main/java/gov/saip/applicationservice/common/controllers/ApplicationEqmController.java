package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApplicationEqmDto;
import gov.saip.applicationservice.common.dto.ApplicationEqmSearchRequestDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.service.ApplicationEqmService;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/application-eqm", "/internal-calling/application-eqm"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationEqmController {
    
    private final ApplicationEqmService applicationEqmService;

    @GetMapping("")
    public PaginationDto<List<ApplicationEqmDto>> listEqmApplicationsFor(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE, value = "limit") int limit,
            @RequestParam(name = "target") String target,
            @ModelAttribute ApplicationEqmSearchRequestDto applicationEqmSearchRequestDto) {
        return applicationEqmService.listEqmApplicationsFor(page, limit, target, applicationEqmSearchRequestDto);
    }

    
}