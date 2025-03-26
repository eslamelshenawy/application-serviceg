package gov.saip.applicationservice.common.service.lookup.impl;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.base.service.BaseServiceImpl;
import gov.saip.applicationservice.common.model.LkApplicationService;
import gov.saip.applicationservice.common.repository.lookup.LkApplicationServiceRepository;
import gov.saip.applicationservice.common.service.lookup.LKApplicationServiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LKApplicationServiceServiceImpl extends BaseServiceImpl<LkApplicationService, Long> implements LKApplicationServiceService {

    private final LkApplicationServiceRepository lkApplicationPriorityStatusRepository;
    @Override
    protected BaseRepository<LkApplicationService, Long> getRepository() {
        return lkApplicationPriorityStatusRepository;
    }
      @Override
      public LkApplicationService update(LkApplicationService lkApplicationService){
          LkApplicationService currentLkApplicationService = getReferenceById(lkApplicationService.getId());
          currentLkApplicationService.setNameAr(lkApplicationService.getNameAr());
          currentLkApplicationService.setNameEn(lkApplicationService.getNameEn());
          currentLkApplicationService.setOperationNumber(lkApplicationService.getOperationNumber());
          return super.update(currentLkApplicationService);
      }

    @Override
    public LkApplicationService findByCode(String serviceCode) {
        return lkApplicationPriorityStatusRepository.findByCode(serviceCode);
    }
}
