package gov.saip.applicationservice.common.mapper.patent;



import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.patent.LkApplicationCollaborativeResearchDto;
import gov.saip.applicationservice.common.model.patent.LkApplicationCollaborativeResearch;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper()
public interface LkApplicationCollaborativeResearchMapper extends BaseMapper<LkApplicationCollaborativeResearch, LkApplicationCollaborativeResearchDto> {

    List<LkApplicationCollaborativeResearchDto> map(List<LkApplicationCollaborativeResearch> patentDetailsDto);
}