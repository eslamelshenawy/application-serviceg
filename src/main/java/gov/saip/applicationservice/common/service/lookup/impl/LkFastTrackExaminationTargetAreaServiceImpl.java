package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.LkFastTrackExaminationTargetAreaDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.mapper.LkFastTrackExaminationTargetAreaMapper;
import gov.saip.applicationservice.common.model.LkDocumentType;
import gov.saip.applicationservice.common.model.LkFastTrackExaminationTargetArea;
import gov.saip.applicationservice.common.repository.lookup.LkFastTrackExaminationTargetAreaRepository;
import gov.saip.applicationservice.common.service.lookup.LkFastTrackExaminationTargetAreaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LkFastTrackExaminationTargetAreaServiceImpl extends BaseServiceImpl<LkFastTrackExaminationTargetArea, Long> implements LkFastTrackExaminationTargetAreaService {

    private final LkFastTrackExaminationTargetAreaRepository fastTrackExaminationTargetAreaRepository;

    private final LkFastTrackExaminationTargetAreaMapper lkFastTrackExaminationTargetAreaMapper;

    @Override
    protected BaseRepository<LkFastTrackExaminationTargetArea, Long> getRepository() {
        return fastTrackExaminationTargetAreaRepository;
    }

    public List<LkFastTrackExaminationTargetAreaDto> getAllFastTrackExaminationTargetAreas() {
        List<LkFastTrackExaminationTargetArea> lkFastTrackExaminationTargetArea = fastTrackExaminationTargetAreaRepository.findAll();
        return lkFastTrackExaminationTargetAreaMapper.mapRequestToEntity(lkFastTrackExaminationTargetArea);
    }

    @Override
    public PaginationDto getAllPaginatedFastTrackExaminationAreaWithSearch(int page, int limit, String search) {
        Pageable pageable = PageRequest.of(page, limit);
        Page<LkFastTrackExaminationTargetArea> lkFastTrackExaminationTargetAreas = fastTrackExaminationTargetAreaRepository.getAllPaginatedFastTrackExaminationAreaWithSearch(search ,pageable);
        return PaginationDto.builder()
                .content(lkFastTrackExaminationTargetAreas.getContent())
                .totalPages(lkFastTrackExaminationTargetAreas.getTotalPages())
                .totalElements(lkFastTrackExaminationTargetAreas.getTotalElements())
                .build();
    }


}
