package gov.saip.applicationservice.modules.plantvarieties.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.modules.plantvarieties.dto.FillingRequestInOtherCountryDto;
import gov.saip.applicationservice.modules.plantvarieties.model.FillingRequestInOtherCountry;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FillingRequestInOtherCountryPlantVarietyRepository extends BaseRepository<FillingRequestInOtherCountry, Long> {


    @Query("""
    select c
    from FillingRequestInOtherCountry c where c.plantVarietyDetailsId = :plantVarietyDetailsId
    """)
    List<FillingRequestInOtherCountry> findAllByPlantDetailsId(@Param("plantVarietyDetailsId") Long plantVarietyDetailsId);


}