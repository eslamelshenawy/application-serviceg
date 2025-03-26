package gov.saip.applicationservice.common.service;

import gov.saip.applicationservice.common.dto.TermsAndConditionsDto;
import gov.saip.applicationservice.common.model.TermsAndConditions;
import org.springframework.stereotype.Service;

import java.util.List;

public interface TermsAndConditionsService {
    List<TermsAndConditionsDto> getAllTermsAndConditionsSorted();

    List<TermsAndConditionsDto> getTermsAndConditionsSorted(String appCategory, String requestType);
}
