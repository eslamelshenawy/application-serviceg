package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.SearchedTrademarkDto;
import gov.saip.applicationservice.common.model.SearchedTrademark;
import gov.saip.applicationservice.common.service.SearchedTrademarkService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/pb/searched-trademark", "/internal-calling/searched-trademark"})
@RequiredArgsConstructor
public class SearchedTrademarkController {

    private final SearchedTrademarkService searchedTrademarkService;

    @PostMapping("/save")
    public ApiResponse<SearchedTrademarkDto> saveSearchedTrademark(@RequestBody SearchedTrademarkDto searchedTrademarkDto) {
        return ApiResponse.ok(searchedTrademarkService.saveSearchedTrademark(searchedTrademarkDto));
    }
    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteSearchedTrademark(@PathVariable Long id) {
        searchedTrademarkService.deleteSearchedTrademark(id);
        return ApiResponse.ok();
    }


    @GetMapping("/applicationId/{applicationInfoId}")
    public ApiResponse<List<SearchedTrademarkDto>> getSearchedTrademarks(@PathVariable Long applicationInfoId) {
        return ApiResponse.ok(searchedTrademarkService.getSearchedTrademarksByApplication(applicationInfoId));
    }
}
