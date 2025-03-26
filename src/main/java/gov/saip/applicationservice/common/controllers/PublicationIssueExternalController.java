package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.publication.IssueDateDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.enums.Publication.PublicationIssueStatusEnum;
import gov.saip.applicationservice.common.model.UnpublishedPublicationIssueProjection;
import gov.saip.applicationservice.common.service.PublicationIssueService;
import gov.saip.applicationservice.util.DownloadFileUtils;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static gov.saip.applicationservice.util.Constants.MODIFIED_PUBLICATION_DATE;

@RestController
@RequestMapping(value = {"/kc/publication-issue", "/internal-calling/publication-issue"})
@RequiredArgsConstructor
@Slf4j
public class PublicationIssueExternalController {

    private final PublicationIssueService publicationIssueService;

    @GetMapping(path = "/{application-category-saip-code}", params = "published=false")
    public ApiResponse<List<UnpublishedPublicationIssueProjection>> getUnpublishedPublicationIssuesByApplicationCategory(
            @PathVariable("application-category-saip-code") ApplicationCategoryEnum applicationCategorySaipCode,
            @RequestParam(value = "publicationIssueStatus", required = false) List<PublicationIssueStatusEnum> publicationIssueStatusEnums) {
        return ApiResponse.ok(
                publicationIssueService.getUnpublishedPublicationIssuesByApplicationCategory(applicationCategorySaipCode, publicationIssueStatusEnums)
        );
    }

    /**
     * modify the given issue date.
     *
     * @param issueDateDto
     * @return an {@link ApiResponse} contains success String
     */
    @PutMapping("/modify/issue-date")
    public ApiResponse<String> updatePublicationDate(@RequestBody IssueDateDto issueDateDto) {
        publicationIssueService.setIssueDate(issueDateDto.getIssueId(), issueDateDto.getDate());
        return ApiResponse.ok(MODIFIED_PUBLICATION_DATE);
    }
    
    @PutMapping("/{id}/status/{code}")
    public void changePublicationIssueStatus(@PathVariable (name="id") Long id
            , @PathVariable (name="code") String code){
        publicationIssueService.changePublicationIssueStatus(id, code);
    }
    
    @PutMapping("/{id}/status/{code}/applications/change-status")
    public void changePublicationIssueStatusAndApplicationsStatuses(@PathVariable (name="id") Long id
            , @PathVariable (name="code") String code){
        publicationIssueService.changePublicationIssueStatusAndApplicationsStatuses(id, code );
    }

    /**
     * Download XML formatted file that contains trademark applications information
     *
     * @param issueId the issue ID of the applications
     * @return an {@link ResponseEntity} containing the {@link ByteArrayResource} which is the XML file
     */
    @GetMapping("/application-info/trademark/{issueId}/xml-file")
    public ResponseEntity<ByteArrayResource> getTrademarkApplicationsInfoXmlByIssueId(
            @PathVariable(value = "issueId") Long issueId) {
        ByteArrayResource file = publicationIssueService.getTrademarkApplicationsInfoXmlByIssueId(issueId);
        return DownloadFileUtils.builder()
                .file(file)
                .downloadedFileName("Trademarks.xml")
                .build();
    }

    /**
     * Download XML formatted file that contains patent applications information
     *
     * @param issueId the issue ID of the applications
     * @return an {@link ResponseEntity} containing the {@link ByteArrayResource} which is the XML file
     */
    @GetMapping("/application-info/patent/{issueId}/xml-file")
    public ResponseEntity<ByteArrayResource> getPatentApplicationsInfoXmlByIssueId(
            @PathVariable(value = "issueId") Long issueId) {
        ByteArrayResource file = publicationIssueService.getPatentApplicationsInfoXmlByIssueId(issueId);
        return DownloadFileUtils.builder()
                .file(file)
                .downloadedFileName("Patents.xml")
                .build();
    }

    /**
     * Download XML formatted file that contains industrial design applications information
     *
     * @param issueId the issue ID of the applications
     * @return an {@link ResponseEntity} containing the {@link ByteArrayResource} which is the XML file
     */
    @GetMapping("/application-info/industrial-design/{issueId}/xml-file")
    public ResponseEntity<ByteArrayResource> getIndustrialDesignApplicationsInfoXmlByIssueId(
            @PathVariable(value = "issueId") Long issueId) {
        ByteArrayResource file = publicationIssueService.getIndustrialDesignApplicationsInfoXmlByIssueId(issueId);
        return DownloadFileUtils.builder()
                .file(file)
                .downloadedFileName("IndustrialDesigns.xml")
                .build();
    }

}
