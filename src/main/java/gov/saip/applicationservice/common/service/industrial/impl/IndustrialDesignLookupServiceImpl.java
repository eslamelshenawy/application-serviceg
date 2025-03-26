package gov.saip.applicationservice.common.service.industrial.impl;

import gov.saip.applicationservice.common.dto.industrial.IndustrialDesignLookupResDto;
import gov.saip.applicationservice.common.mapper.industrial.LkShapeMapper;
import gov.saip.applicationservice.common.service.industrial.IndustrialDesignLookupService;
import gov.saip.applicationservice.common.service.industrial.LkShapeService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class IndustrialDesignLookupServiceImpl implements IndustrialDesignLookupService {

    private final LkShapeService lkShapeService;
    private final LkShapeMapper lkShapeMapper;

    @Override
    public IndustrialDesignLookupResDto getIndustrialDesignLookup() {
        return IndustrialDesignLookupResDto
                .builder()
                .shapes(lkShapeMapper.map(lkShapeService.findAll()))
                .build();
    }
}
