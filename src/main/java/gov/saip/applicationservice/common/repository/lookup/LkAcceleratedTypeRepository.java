package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.LkAcceleratedType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LkAcceleratedTypeRepository extends BaseRepository<LkAcceleratedType, Long> {
    List<LkAcceleratedType> findByApplicationCategory_Id(Long categoryId);
}
