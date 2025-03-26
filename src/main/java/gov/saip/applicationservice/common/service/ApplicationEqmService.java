package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationEqmDto;
import gov.saip.applicationservice.common.dto.ApplicationEqmSearchRequestDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;

import java.util.List;

public interface ApplicationEqmService {
    PaginationDto<List<ApplicationEqmDto>> listEqmApplicationsFor(int page, int limit, String target,
                                                                  ApplicationEqmSearchRequestDto applicationEqmSearchRequestDto);

}