package gov.saip.applicationservice.common.mapper;


import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.common.dto.TermsAndConditionsDto;
import gov.saip.applicationservice.common.model.TermsAndConditions;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Mapper()
@Component
public interface TermsAndConditionsMapper extends BaseMapper<TermsAndConditions, TermsAndConditionsDto> {
    List<TermsAndConditionsDto> mapRequestToEntity(List<TermsAndConditions> termsAndConditions);
}