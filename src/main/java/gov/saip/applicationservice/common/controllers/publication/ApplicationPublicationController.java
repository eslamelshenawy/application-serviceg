package gov.saip.applicationservice.common.controllers.publication;

import java.time.Clock;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.SortRequestDto;
import gov.saip.applicationservice.common.dto.publication.PublicPublicationSearchParamDto;
import gov.saip.applicationservice.common.enums.PublicationTargetEnum;
import gov.saip.applicationservice.common.facade.publication.PublicationIssueFacade;
import gov.saip.applicationservice.common.model.ApplicationCategoryToPublicationCountProjection;
import gov.saip.applicationservice.common.service.ApplicationPublicationService;
import gov.saip.applicationservice.common.service.PublicationService;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;


@RestController
@RequestMapping(value = {"/pb", "/internal-calling"})
@RequiredArgsConstructor
public class ApplicationPublicationController {
    
    private final PublicationService publicationService;
    private final PublicationIssueFacade publicationIssueFacade;
    private final ApplicationPublicationService applicationPublicationService;
    private final Clock clock;
    
    @GetMapping("/publications-batch-list/trademark/{publicationTarget}")
    public ApiResponse<PaginationDto> listGazettePublicationsForTrademark(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(value = "publicationIssueId", required = false) Long publicationIssueId,
            @ModelAttribute PublicPublicationSearchParamDto publicPublicationSearchParamDto,
            @ModelAttribute SortRequestDto sortRequestDto,
            @PathVariable(name = "publicationTarget") PublicationTargetEnum publicationTarget) {
        
        return ApiResponse.ok(publicationService.listGazetteOrPublicationsForTrademark(page, limit,
                publicationIssueId, publicationTarget.name(), publicPublicationSearchParamDto, sortRequestDto));
    }
    
    @GetMapping("/publications-batch-list/patent/{publicationTarget}")
    public ApiResponse<PaginationDto> listGazettePublicationsForPatent(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(value = "publicationIssueId", required = false) Long publicationIssueId,
            @ModelAttribute PublicPublicationSearchParamDto publicPublicationSearchParamDto,
            @ModelAttribute SortRequestDto sortRequestDto,
            @PathVariable(name = "publicationTarget") PublicationTargetEnum publicationTarget) {
        return ApiResponse.ok(publicationService.listGazetteOrPublicationsForPatent(page, limit,
                publicationIssueId, publicationTarget.name(), publicPublicationSearchParamDto, sortRequestDto));
    }
    
    @GetMapping("/publications-batch-list/industrial_design/{publicationTarget}")
    public ApiResponse<PaginationDto> listGazettePublicationsForIndustrial(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(value = "publicationIssueId", required = false) Long publicationIssueId,
            @ModelAttribute PublicPublicationSearchParamDto publicPublicationSearchParamDto,
            @ModelAttribute SortRequestDto sortRequestDto,
            @PathVariable(name = "publicationTarget") PublicationTargetEnum publicationTarget) {
        return ApiResponse.ok(publicationService.listGazetteOrPublicationsForIndustrial(page, limit,
                publicationIssueId, publicationTarget.name(), publicPublicationSearchParamDto, sortRequestDto));
    }
    
    @GetMapping("/application-publication/count-by-application-category")
    public ApiResponse<List<ApplicationCategoryToPublicationCountProjection>> countPublicationsByApplicationCategory() {
        List<ApplicationCategoryToPublicationCountProjection> countsByApplicationCategory = publicationService.countPublicationsByApplicationCategory(clock);
        return ApiResponse.ok(countsByApplicationCategory);
    }
    
    @PostMapping("/application-publication/{applicationId}")
    public ApiResponse<Void> createApplicationPublication(@PathVariable("applicationId") Long applicationId
            , @RequestParam(value = "publicationType", required = false) String publicationType) {
        applicationPublicationService.createApplicationPublication(applicationId, publicationType);
        return ApiResponse.ok();
    }
    
    @PostMapping("/application-publication/{applicationId}/publish-in-next-issue/granted")
    public ApiResponse<Void> publishGrantedApplicationPublicationInNextIssue(@PathVariable("applicationId") Long applicationId) {
        publicationIssueFacade.addGrantedApplicationPublicationToLatestIssueOrCreateNewIssue(applicationId);
        return ApiResponse.ok();
    }
    
    @PostMapping("/application-publication/{applicationId}/publish-in-next-issue")
    public ApiResponse<Void> publishApplicationPublicationInNextIssue(@PathVariable("applicationId") Long applicationId,
                                                                      @RequestParam(value = "publicationType") String publicationType) {
        publicationIssueFacade.addApplicationPublicationToLatestIssueOrCreateNewIssue(applicationId, publicationType);
        return ApiResponse.ok();
    }
    
    @PostMapping("/crossed-out-mark-application-publication/{applicationId}/publish-in-next-issue")
    public ApiResponse<Void> addApplicationPublicationToLatestIssueOrCreateNewIssue(@PathVariable("applicationId") Long applicationId,
                                                                                    @RequestParam(value = "publicationType") String publicationType) {
        publicationIssueFacade.addApplicationPublicationToLatestIssueOrCreateNewIssue(applicationId, publicationType);
        return ApiResponse.ok();
    }
    
    
}
