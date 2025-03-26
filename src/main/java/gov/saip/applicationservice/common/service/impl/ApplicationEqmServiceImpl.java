package gov.saip.applicationservice.common.service.impl;

import gov.saip.applicationservice.common.dto.ApplicationEqmDto;
import gov.saip.applicationservice.common.dto.ApplicationEqmSearchRequestDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.common.service.ApplicationEqmService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

import static gov.saip.applicationservice.util.Constants.EQM_OPPOSITION;
import static gov.saip.applicationservice.util.Constants.MAP_EQM_APPLICATION_STATUSES;

@Service
@RequiredArgsConstructor
public class ApplicationEqmServiceImpl  implements ApplicationEqmService  {
    
    private final ApplicationInfoRepository applicationInfoRepository;
    @Override
    public PaginationDto<List<ApplicationEqmDto>> listEqmApplicationsFor(int page, int limit, String target,
                                                                         ApplicationEqmSearchRequestDto applicationEqmSearchRequestDto) {
        
        Pageable pageable = PageRequest.of(page, limit);
        List<String> appStatuses = null;
        List<String> eqmStatuses = null;
        
        if(target.equals(EQM_OPPOSITION)) {
            eqmStatuses = getEqmApplicationStatuses(target);
        }
        else {
            appStatuses = getEqmApplicationStatuses(target);
        }
        Page<ApplicationEqmDto> applicationEqmDtos = applicationInfoRepository.listEqmApplicationsFor(appStatuses, eqmStatuses,
                applicationEqmSearchRequestDto.getSearchField(), null,
                applicationEqmSearchRequestDto.getFromDate(), applicationEqmSearchRequestDto.getToDate(),  pageable);
        
        PaginationDto<List<ApplicationEqmDto>> paginationDto = PaginationDto.fromPage(applicationEqmDtos);
        return paginationDto;
    }
    
    private List<String> getEqmApplicationStatuses(String target) {
        return MAP_EQM_APPLICATION_STATUSES.get(target);
    }
}

