package gov.saip.applicationservice.common.repository.agency;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.agency.LKTrademarkAgencyRequestStatus;
import org.springframework.stereotype.Repository;

@Repository
public interface LKTrademarkAgencyRequestStatusRepository extends BaseLkRepository<LKTrademarkAgencyRequestStatus, Integer> {


    LKTrademarkAgencyRequestStatus getLKTrademarkAgencyRequestStatusByCode(String code);
}
