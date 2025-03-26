package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.common.dto.PctIValidateDto;
import gov.saip.applicationservice.common.model.ApplicationSearch;
import gov.saip.applicationservice.common.model.PetitionRequestNationalStage;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;


@Repository
public interface PetitionRequestNationalStageRepository extends SupportServiceRequestRepository<PetitionRequestNationalStage>{




    @Query("""
            select case when (count(prns) > 0) then true else false end from PetitionRequestNationalStage prns where prns.requestNumber= :requestNumber and prns.requestStatus.code='COMPLETED'
            """)
    public boolean checkPetitionRequestNumberToUseInPct(@Param("requestNumber") String requestNumber);

    @Query("""
            select new gov.saip.applicationservice.common.dto.PctIValidateDto( (count(prns) > 0) ,prns.globalApplicationNumber) from PetitionRequestNationalStage prns where prns.requestNumber= :requestNumber 
            and prns.requestStatus.code='COMPLETED'
            group by prns.globalApplicationNumber
            """)
    public PctIValidateDto checkPetitionRequestNumber(@Param("requestNumber") String requestNumber);


    @Query(""" 
            select prns.processRequestId from PetitionRequestNationalStage prns
            where prns.requestStatus.code='COMPLETED'
            and prns.globalApplicationNumber = (
            select p.pctApplicationNo from Pct p
            join p.patentDetails pd
            where pd.applicationId = :applicationId and p.petitionNumber = prns.requestNumber
             )
            """)
    Long getSupportServicesProcessRequestId(@Param("applicationId")Long applicationId);
}
