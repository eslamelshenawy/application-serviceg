package gov.saip.applicationservice.common.controllers.patent;


import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.common.dto.patent.LkApplicationCollaborativeResearchDto;
import gov.saip.applicationservice.common.mapper.patent.LkApplicationCollaborativeResearchMapper;
import gov.saip.applicationservice.common.model.patent.LkApplicationCollaborativeResearch;
import gov.saip.applicationservice.common.service.patent.LkApplicationCollaborativeResearchService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping({"kc/applicationCollaborativeResearch", "/internal-calling/applicationCollaborativeResearch"})
@RequiredArgsConstructor
@Getter
public class LkApplicationCollaborativeResearchController extends BaseController<LkApplicationCollaborativeResearch, LkApplicationCollaborativeResearchDto, Long> {

    private final LkApplicationCollaborativeResearchService service;
    private final LkApplicationCollaborativeResearchMapper mapper;
}
