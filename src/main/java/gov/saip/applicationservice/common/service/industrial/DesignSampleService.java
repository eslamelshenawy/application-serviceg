package gov.saip.applicationservice.common.service.industrial;


import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.industrial.CreateDesignSampleDto;
import gov.saip.applicationservice.common.dto.industrial.DesignSampleReqDto;
import gov.saip.applicationservice.common.dto.industrial.DesignSampleResDto;
import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignDetailDto;
import gov.saip.applicationservice.common.model.industrial.DesignSample;

import java.util.List;

public interface DesignSampleService extends BaseService<DesignSample, Long> {


    IndustrialDesignDetailDto create(CreateDesignSampleDto designSampleReqDtos);

   Long count(Long designId);

    DesignSampleReqDto updateDesigners(DesignSampleReqDto designSampleReqDto) ;

    DesignSampleReqDto updateClassifications(DesignSampleReqDto designSampleReqDto);

    DesignSampleReqDto updateName(DesignSampleReqDto designSampleReqDto);

    void deleteSample(Long id);

    PaginationDto<List<DesignSampleResDto>> findDesignSamplesByIndustrialDesignId(Long industrialDesignId,String query ,Boolean withDescription , Integer page, Integer limit);

    DesignSampleResDto processSample(DesignSampleResDto sample);

    DesignSampleReqDto updateDescription(DesignSampleReqDto designSampleReqDto);

    List<DesignSample> findDesignSamplesByAppId(Long appId);
    void deleteWithOldLocarno();
}
