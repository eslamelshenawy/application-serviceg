package gov.saip.applicationservice.common.service;

import feign.Param;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.SimilarTrademarkDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.SimilarTrademark;

import java.util.List;


public interface SimilarTrademarkService extends BaseService<SimilarTrademark, Long> {
    List<SimilarTrademarkDto> getTaskInstanceId(String taskInstanceId);
    int softDelete(long id);
    List<SimilarTrademarkDto> findByApplicationInfoId(Long applicationInfo);

}
