package gov.saip.applicationservice.common.service.appeal.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.appeal.AppealCommitteeOpinion;
import gov.saip.applicationservice.common.repository.appeal.AppealCommitteeOpinionRepository;
import gov.saip.applicationservice.common.service.appeal.AppealCommitteeOpinionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AppealCommitteeOpinionServiceImpl extends BaseServiceImpl<AppealCommitteeOpinion, Long> implements AppealCommitteeOpinionService {

    private final AppealCommitteeOpinionRepository appealCommitteeOpinionRepository;
    @Override
    protected BaseRepository<AppealCommitteeOpinion, Long> getRepository() {
        return appealCommitteeOpinionRepository;
    }
}
