package gov.saip.applicationservice.modules.plantvarieties.service.impl;


import gov.saip.applicationservice.base.service.BaseLkServiceImpl;
import gov.saip.applicationservice.modules.plantvarieties.model.LKPlantDetails;
import gov.saip.applicationservice.modules.plantvarieties.repository.LKPlantDetailsRepository;
import gov.saip.applicationservice.modules.plantvarieties.service.LKPlantDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LKPlantDetailsServiceImpl extends BaseLkServiceImpl<LKPlantDetails,Integer>
implements LKPlantDetailsService {


    private final LKPlantDetailsRepository lkPlantDetailsRepository;
    @Override
    public List<LKPlantDetails> findByMainCode(String mainCode) {
        return lkPlantDetailsRepository.findByMainCode(mainCode);
    }
}
