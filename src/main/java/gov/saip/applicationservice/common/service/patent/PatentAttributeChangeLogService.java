package gov.saip.applicationservice.common.service.patent;


import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.patent.PatentAttributeChangeLogDto;
import gov.saip.applicationservice.common.dto.patent.PatentDetailsRequestDto;
import gov.saip.applicationservice.common.model.patent.PatentAttributeChangeLog;
import gov.saip.applicationservice.common.model.patent.PatentDetails;

import java.util.List;
import java.util.Map;

public interface PatentAttributeChangeLogService extends BaseService<PatentAttributeChangeLog, Long> {

    void savePatentAttributeChangeLog(PatentDetails patentDetails, PatentDetailsRequestDto patentDetailsRequestDto);
    Map<String, List<PatentAttributeChangeLogDto>> getPatentAttributeChangeLogByPatentId(Long patentId);

    Map<String, List<PatentAttributeChangeLogDto>> getPatentAttributeLatestChangeLogByPatentId(Long patentId);

    String getAttributeValueByApplicationId(Long applicationId, String attributeName);

    public Map<String, List<PatentAttributeChangeLogDto>> getPatentAttributesLatestChangeLogByPatentId(List<String> attributeNames, Long patentId);
}
