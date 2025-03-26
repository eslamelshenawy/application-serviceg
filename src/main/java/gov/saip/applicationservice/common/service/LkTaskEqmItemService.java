package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.base.service.BaseLkService;
import gov.saip.applicationservice.common.dto.LkTaskEqmItemDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.model.LkTaskEqmItem;

import java.util.List;

public interface LkTaskEqmItemService extends BaseLkService<LkTaskEqmItem, Integer> {

    List<LkTaskEqmItem> findByTypeCode(String typeCode);

    LkTaskEqmItem updateDto(LkTaskEqmItem entity);

    PaginationDto getAllPaginatedLKTaskEqm(String search , int page , int limit);
}
