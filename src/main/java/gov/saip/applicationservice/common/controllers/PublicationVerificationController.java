package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.enums.PublicationTargetEnum;
import gov.saip.applicationservice.common.service.PublicationService;
import gov.saip.applicationservice.util.Constants;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping( "/internal-calling")
@RequiredArgsConstructor
public class PublicationVerificationController {
    
    private final PublicationService publicationService;
    
    @GetMapping("/publications/patent")
    public ApiResponse<PaginationDto> getPatentApplicationPublications(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE, value = "limit") int limit,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate){
        return ApiResponse.ok(publicationService.getPatentPublicationsBatches(page, limit, fromDate, toDate));
    }
    
    @GetMapping("/publications/trademark")
    public ApiResponse<PaginationDto> getTrademarkApplicationPublications(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE, value = "limit") int limit,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate){
        return ApiResponse.ok(publicationService.getTrademarkPublicationsBatches(page, limit, fromDate, toDate));
    }
    
    @GetMapping("/publications/industrial_design")
    public ApiResponse<PaginationDto> getIndustrialApplicationPublications(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE, value = "limit") int limit,
            @RequestParam(value = "fromDate", required = false) String fromDate,
            @RequestParam(value = "toDate", required = false) String toDate){
        return ApiResponse.ok(publicationService.getIndustrialPublicationsBatches(page, limit, fromDate, toDate));
    }
    
    @GetMapping("/publications-batch-view/trademark")
    public ApiResponse<PaginationDto> viewTrademarkPublications(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE, value = "limit") int limit,
            @RequestParam(value = "receptionDate", required = false) String receptionDate){
        return ApiResponse.ok(publicationService.viewTrademarkPublications(page, limit, receptionDate));
    }
    
    @GetMapping("/publications-batch-view/patent")
    public ApiResponse<PaginationDto> viewPatentPublications(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE, value = "limit") int limit,
            @RequestParam(value = "receptionDate", required = false) String receptionDate){
        return ApiResponse.ok(publicationService.viewPatentPublications(page, limit, receptionDate));
    }
    
    @GetMapping("/publications-batch-view/industrial_design")
    public ApiResponse<PaginationDto> viewIndustrialPublications(
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_NUMBER, value = "page") int page,
            @RequestParam(required = false, defaultValue = Constants.DEFAULT_PAGE_SIZE, value = "limit") int limit,
            @RequestParam(value = "receptionDate", required = false) String receptionDate){
        return ApiResponse.ok(publicationService.viewIndustrialPublications(page, limit, receptionDate));
    }
    

}
