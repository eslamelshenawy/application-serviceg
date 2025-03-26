package gov.saip.applicationservice.common.service.lookup;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.LkFastTrackExaminationTargetAreaDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.model.LkFastTrackExaminationTargetArea;
import org.springframework.stereotype.Service;

import java.util.List;

public interface LkFastTrackExaminationTargetAreaService extends BaseService<LkFastTrackExaminationTargetArea, Long> {

    List<LkFastTrackExaminationTargetAreaDto> getAllFastTrackExaminationTargetAreas();

    PaginationDto getAllPaginatedFastTrackExaminationAreaWithSearch(int page , int limit , String search);

}
