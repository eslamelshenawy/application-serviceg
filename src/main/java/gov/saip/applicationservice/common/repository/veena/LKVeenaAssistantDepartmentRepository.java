package gov.saip.applicationservice.common.repository.veena;

import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.veena.LKVeenaAssistantDepartment;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LKVeenaAssistantDepartmentRepository extends BaseLkRepository<LKVeenaAssistantDepartment, Long> {

    List<LKVeenaAssistantDepartment> getByVeenaDepartmentId(Long id);

    @Query("Select veena.veenaAssistantDepartment from ApplicationVeenaClassification veena where veena.application.id= :appId")
    List<LKVeenaAssistantDepartment> getVeenaAssistantDepartmentsByApplicationId(@Param("appId") Long appId);




    @Query("""
            select distinct veena from LKVeenaAssistantDepartment veena
            Where (:search IS NULL
            OR UPPER(veena.code) LIKE UPPER(concat('%', :search, '%'))
            OR UPPER(veena.nameAr) LIKE UPPER(concat('%', :search, '%'))
            OR UPPER(veena.nameEn) LIKE UPPER(concat('%', :search, '%')))
            """)
    List<LKVeenaAssistantDepartment> searchVeenaAssistantDepartment(@Param("search")String search);

}
