package gov.saip.applicationservice.common.service.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.SupportingEvidenceDto;
import gov.saip.applicationservice.common.mapper.SupportingEvidenceMapper;
import gov.saip.applicationservice.common.model.*;
import gov.saip.applicationservice.common.repository.SupportingEvidenceRepository;
import gov.saip.applicationservice.common.service.SupportingEvidenceService;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Setter
public class SupportingEvidenceServiceImpl extends BaseServiceImpl<SupportingEvidence, Long> implements SupportingEvidenceService {

    private final SupportingEvidenceRepository supportingEvidenceRepository;
    private final SupportingEvidenceMapper supportingEvidenceMapper;

    @Override
    protected BaseRepository<SupportingEvidence, Long> getRepository() {
        return supportingEvidenceRepository;
    }
    @Override
    public void deleteBySupportEvIdId(Long appId) {
        supportingEvidenceRepository.updateIsDeleted(appId,1);
    }

    @Override
    public void updateSupportingEvidence(SupportingEvidenceDto supportingEvidenceDto) {
            SupportingEvidence supportingEvidence = supportingEvidenceMapper.unMap(supportingEvidenceDto);
            supportingEvidenceRepository.save(supportingEvidence);
    }

    @Override
    public PaginationDto<List<SupportingEvidenceDto>> getSupportingEvidenceForApplicationsInfo(Long appId, Integer page, Integer limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by("id").descending());
        Page<SupportingEvidence> SupportingEvidencePages = supportingEvidenceRepository.getSupportingEvidenceForApplicationInfo(appId, pageable);
        return PaginationDto.<List<SupportingEvidenceDto>>builder()
                .content(supportingEvidenceMapper.map(SupportingEvidencePages.getContent()))
                .totalPages(SupportingEvidencePages.getTotalPages())
                .totalElements(SupportingEvidencePages.getTotalElements())
                .build();
    }

}
