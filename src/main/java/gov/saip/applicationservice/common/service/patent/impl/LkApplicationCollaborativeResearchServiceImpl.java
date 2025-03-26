package gov.saip.applicationservice.common.service.patent.impl;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.dto.patent.LkApplicationCollaborativeResearchDto;
import gov.saip.applicationservice.common.mapper.patent.LkApplicationCollaborativeResearchMapper;
import gov.saip.applicationservice.common.model.patent.LkApplicationCollaborativeResearch;
import gov.saip.applicationservice.common.repository.patent.LkApplicationCollaborativeResearchRepository;
import gov.saip.applicationservice.common.service.patent.LkApplicationCollaborativeResearchService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The {@code LkApplicationCollaborativeResearchServiceImpl} class provides an implementation of the
 * {@link LkApplicationCollaborativeResearchService} interface that manages collaborative research applications.
 */
@Service
@RequiredArgsConstructor
public class LkApplicationCollaborativeResearchServiceImpl extends BaseServiceImpl<LkApplicationCollaborativeResearch, Long> implements LkApplicationCollaborativeResearchService {

    private final LkApplicationCollaborativeResearchRepository applicationCollaborativeResearchRepository;
    private final LkApplicationCollaborativeResearchMapper applicationCollaborativeResearchMapper;
    @Override
    protected BaseRepository<LkApplicationCollaborativeResearch, Long> getRepository() {
        return applicationCollaborativeResearchRepository;
    }
    /**
     * Constructs a new {@code LkApplicationCollaborativeResearchServiceImpl} instance with the specified repository and mapper.
     *
     * @param applicationCollaborativeResearchRepository the collaborative research application repository
     * @param applicationCollaborativeResearchMapper the collaborative research application mapper
     */

    /**
     * Gets all collaborative research applications.
     *
     * @return a list of {@link LkApplicationCollaborativeResearchDto} objects
     */
    public List<LkApplicationCollaborativeResearchDto> getAllApplicationCollaborativeResearch() {
        List<LkApplicationCollaborativeResearch> lkApplicationCollaborativeResearches = applicationCollaborativeResearchRepository.findAll();
        return applicationCollaborativeResearchMapper.map(lkApplicationCollaborativeResearches);
    }
}
