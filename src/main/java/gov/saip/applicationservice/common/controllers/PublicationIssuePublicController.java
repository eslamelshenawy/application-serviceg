package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.PublicationYearsDto;
import gov.saip.applicationservice.common.dto.publication.PublicPublicationSearchParamDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.Publication.PublicationIssueStatusEnum;
import gov.saip.applicationservice.common.model.ApplicationCategoryToIssueCountProjection;
import gov.saip.applicationservice.common.model.PublicationIssueProjection;
import gov.saip.applicationservice.common.service.PublicationIssueService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

import java.time.Clock;
import java.util.List;

@RestController
@RequestMapping(value = {"/pb/publication-issue", "/internal-calling/publication-issue"})
@RequiredArgsConstructor
@Slf4j
public class PublicationIssuePublicController {

    private final PublicationIssueService publicationIssueService;
    private final Clock clock;

    @GetMapping("/count-published-by-application-category")
    public ApiResponse<List<ApplicationCategoryToIssueCountProjection>> countPublishedIssuesByApplicationCategory() {
        List<ApplicationCategoryToIssueCountProjection> countsByApplicationCategory =
                publicationIssueService.countPublishedIssuesByApplicationCategory(clock);
        return ApiResponse.ok(countsByApplicationCategory);
    }

    @GetMapping(path = "/{application-category-saip-code}", params = "published=true")
    public ApiResponse<PaginationDto<List<PublicationIssueProjection>>> getPublishedPublicationIssuesByApplicationCategory(
            @PathVariable("application-category-saip-code") ApplicationCategoryEnum applicationCategorySaipCode,
            @RequestParam(value = "searchField", required = false) String searchField,
            @RequestParam(value = "page", defaultValue = "0", required = false) Integer page,
            @RequestParam(value = "limit", defaultValue = "10", required = false) Integer limit,
            @ModelAttribute PublicPublicationSearchParamDto publicPublicationSearchParamDto,
            @RequestParam(value = "publicationIssueStatus", required = false) List<PublicationIssueStatusEnum> publicationIssueStatusEnums,
            @RequestParam(value = "sortBy", required = false, defaultValue = "id,issueNumber") String[] sortableColumns,
            @RequestParam(value = "sortOrder", required = false, defaultValue = "DESC") Sort.Direction orderBy) {

        Page<PublicationIssueProjection> publishedPublicationIssues =
                publicationIssueService.getPublishedPublicationIssuesByApplicationCategory(
                        applicationCategorySaipCode,
                        publicPublicationSearchParamDto,
                        publicationIssueStatusEnums,
                        searchField,
                        PageRequest.of(page, limit, Sort.by(orderBy, sortableColumns)),
                        clock);

        PaginationDto<List<PublicationIssueProjection>> paginationDto = PaginationDto.fromPage(publishedPublicationIssues);
        return ApiResponse.ok(paginationDto);
    }

    @GetMapping("/publication-years/{application-category-saip-code}")
    public ApiResponse<PublicationYearsDto> getPublicationYears(@PathVariable("application-category-saip-code") ApplicationCategoryEnum applicationCategorySaipCode) {
        PublicationYearsDto publicationYearsDto = publicationIssueService.getPublicationYears(applicationCategorySaipCode);
        return ApiResponse.ok(publicationYearsDto);
    }

}
