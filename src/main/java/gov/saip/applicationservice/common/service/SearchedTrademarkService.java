package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.SearchedTrademarkDto;
import gov.saip.applicationservice.common.model.SearchedTrademark;

import java.util.List;

public interface SearchedTrademarkService {
    SearchedTrademarkDto saveSearchedTrademark(SearchedTrademarkDto searchedTrademarkDto);
    List<SearchedTrademarkDto> getSearchedTrademarksByApplication(Long applicationInfoId);
    void deleteSearchedTrademark(Long id);

}

