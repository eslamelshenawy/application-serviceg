package gov.saip.applicationservice.common.repository.applicationSnapShot;


import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.dto.ApplicationSnapShotDto.ApplicationSnapShotDropDownMenuResponseDto;
import gov.saip.applicationservice.common.model.ApplicationSnapShot;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationSnapShotRepository extends BaseRepository<ApplicationSnapShot, Long> {

    @Query("""
        select max(applicationSnapShot.versionNumber) 
        from ApplicationSnapShot applicationSnapShot where applicationSnapShot.versionNumber IS NOT NULL 
        and applicationSnapShot.application.id = :appId
    """)
    Integer getApplicationVersionNumber(@Param("appId")Long appId);



    Optional<ApplicationSnapShot> findByVersionNumberAndApplicationId(Integer versionNumber ,Long appId);

    //TODO jhijri date will return and mapped in response
    @Query("""
            select new gov.saip.applicationservice.common.dto.ApplicationSnapShotDto.ApplicationSnapShotDropDownMenuResponseDto(
            applicationSnapShot.id,
            applicationSnapShot.versionNumber,
            applicationSnapShot.createdDate
            ) 
            from  ApplicationSnapShot applicationSnapShot
            where applicationSnapShot.application.id = :appId   
            """)
    Optional<List<ApplicationSnapShotDropDownMenuResponseDto>> getApplicationVersions(@Param("appId") Long appId);

}
