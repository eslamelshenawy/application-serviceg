package gov.saip.applicationservice.common.service.supportService;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.BaseStatusChangeLogDto;
import gov.saip.applicationservice.common.dto.supportService.SupportServiceStatusChangeLogDto;
import gov.saip.applicationservice.common.model.supportService.SupportServiceStatusChangeLog;

import java.util.List;

public interface SupportServiceStatusChangeLogService extends BaseService<SupportServiceStatusChangeLog, Long> {
    List<BaseStatusChangeLogDto> getBySupportServiceId(Long appId);

    SupportServiceStatusChangeLog insert(SupportServiceStatusChangeLogDto entity);
}
