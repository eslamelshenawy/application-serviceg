package gov.saip.applicationservice.common.service.patent;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.patent.LkApplicationCollaborativeResearchDto;
import gov.saip.applicationservice.common.model.patent.LkApplicationCollaborativeResearch;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface LkApplicationCollaborativeResearchService extends BaseService<LkApplicationCollaborativeResearch, Long> {
    List<LkApplicationCollaborativeResearchDto> getAllApplicationCollaborativeResearch();
}
