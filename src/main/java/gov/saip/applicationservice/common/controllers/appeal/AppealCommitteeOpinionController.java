package gov.saip.applicationservice.common.controllers.appeal;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.common.dto.appeal.AppealCommitteeOpinionDto;
import gov.saip.applicationservice.common.mapper.appeal.AppealCommitteeOpinionMapper;
import gov.saip.applicationservice.common.model.appeal.AppealCommitteeOpinion;
import gov.saip.applicationservice.common.service.appeal.AppealCommitteeOpinionService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = {"/kc/appeal-committee-opinion", "/internal-calling/appeal-committee-opinion"})
@RequiredArgsConstructor
@Getter
public class AppealCommitteeOpinionController extends BaseController<AppealCommitteeOpinion, AppealCommitteeOpinionDto, Long> {

    private final AppealCommitteeOpinionService service;
    private final AppealCommitteeOpinionMapper mapper;

}
