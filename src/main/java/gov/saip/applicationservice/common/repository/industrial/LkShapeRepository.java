package gov.saip.applicationservice.common.repository.industrial;


import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.common.model.industrial.LkShapeType;
import org.springframework.stereotype.Repository;

@Repository
public interface LkShapeRepository extends BaseLkRepository<LkShapeType, Long> {
}
