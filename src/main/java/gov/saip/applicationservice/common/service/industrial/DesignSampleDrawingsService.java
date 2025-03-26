package gov.saip.applicationservice.common.service.industrial;


import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.industrial.DesignSampleDrawingsReqDto;
import gov.saip.applicationservice.common.model.industrial.DesignSample;
import gov.saip.applicationservice.common.model.industrial.DesignSampleDrawings;

public interface DesignSampleDrawingsService extends BaseService<DesignSampleDrawings, Long> {

    public DesignSampleDrawings createDesignSampleDrawings(DesignSampleDrawingsReqDto drawingsReqDto, DesignSample designSample);

    public DesignSampleDrawings save(DesignSampleDrawingsReqDto drawingsReqDto, DesignSample designSample);

    void deleteDrawing(Long id);
}
