package gov.saip.applicationservice.common.mapper;


import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.LkFastTrackExaminationTargetAreaDto;
import gov.saip.applicationservice.common.model.LkFastTrackExaminationTargetArea;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper()
public interface LkFastTrackExaminationTargetAreaMapper extends BaseMapper<LkFastTrackExaminationTargetArea, LkFastTrackExaminationTargetAreaDto> {
    List<LkFastTrackExaminationTargetAreaDto> mapRequestToEntity(List<LkFastTrackExaminationTargetArea> lkFastTrackExaminationTargetAreas);
}