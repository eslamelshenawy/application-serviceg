package gov.saip.applicationservice.common.service.patent;


import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.PctIValidateDto;
import gov.saip.applicationservice.common.dto.patent.PctDto;
import gov.saip.applicationservice.common.dto.patent.PctRequestDto;
import gov.saip.applicationservice.common.model.patent.Pct;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface PctService extends BaseService<Pct, Long> {

    Pct createPct(PctRequestDto dto);

    PctDto getPctById(Long id);

    Pct findByApplicationId(Long applicationId);

    Optional<Pct> findPctByApplicationId(Long applicationId);

    LocalDateTime getFillingDateOrInternationalDate(Long applicationId);

    Boolean validatePetitionNumber(String petitionNumber);

    Boolean validatePCT(Long applicationId);

    PctDto findDTOByApplicationId(Long applicationId);

    LocalDate getFilingDateByApplicationNumber(String applicationNumber);
    Pct findByPatentDetailsId(Long patentDetailsId);
    PctIValidateDto validatePetitionNumberAndGetPctNumber(String petitionNumber);

}
