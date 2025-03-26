package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.SortRequestDto;
import gov.saip.applicationservice.common.dto.publication.PublicPublicationSearchParamDto;
import gov.saip.applicationservice.common.model.ApplicationCategoryToPublicationCountProjection;

import java.time.Clock;
import java.util.List;


public interface PublicationService {
    
    PaginationDto getPatentPublicationsBatches(int page, int limit, String fromDate, String toDate);
    
    PaginationDto getTrademarkPublicationsBatches(int page, int limit, String fromDate, String toDate);
    
    PaginationDto getIndustrialPublicationsBatches(int page, int limit, String fromDate, String toDate);
    
    PaginationDto viewTrademarkPublications(int page, int limit, String receptionDate);
    
    PaginationDto viewPatentPublications(int page, int limit, String receptionDate);
    
    PaginationDto viewIndustrialPublications(int page, int limit, String receptionDate);
    
    PaginationDto listGazetteOrPublicationsForTrademark(int page, int limit, Long publicationIssueId, String publicationTarget,
                                                        PublicPublicationSearchParamDto publicPublicationSearchParamDto, SortRequestDto sortRequestDto);
    PaginationDto listGazetteOrPublicationsForPatent(int page, int limit, Long publicationIssueId, String publicationTarget,
                                                     PublicPublicationSearchParamDto publicPublicationSearchParamDto, SortRequestDto sortRequestDto);
    
    PaginationDto listGazetteOrPublicationsForIndustrial(int page, int limit, Long publicationIssueId, String publicationTarget,
                                                     PublicPublicationSearchParamDto publicPublicationSearchParamDto, SortRequestDto sortRequestDto);

    List<ApplicationCategoryToPublicationCountProjection> countPublicationsByApplicationCategory(Clock clock);

}
