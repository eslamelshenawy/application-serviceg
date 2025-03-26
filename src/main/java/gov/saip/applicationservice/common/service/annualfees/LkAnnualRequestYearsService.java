package gov.saip.applicationservice.common.service.annualfees;

import gov.saip.applicationservice.base.service.BaseLkService;
import gov.saip.applicationservice.common.dto.annualfees.LkAnnualRequestYearsDto;
import gov.saip.applicationservice.common.model.annual_fees.LkAnnualRequestYears;

import java.util.List;

public interface LkAnnualRequestYearsService extends BaseLkService<LkAnnualRequestYears, Long> {

    List<LkAnnualRequestYearsDto> getAnnualYearsByAppId(Long appId);

}
