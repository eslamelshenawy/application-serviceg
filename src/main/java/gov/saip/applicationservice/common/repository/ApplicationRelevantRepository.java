package gov.saip.applicationservice.common.repository;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.enums.IdentifierTypeEnum;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.model.ApplicationRelevant;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ApplicationRelevantRepository extends BaseRepository<ApplicationRelevant, Long> {

  Optional< ApplicationRelevant> findByIdentifierIgnoreCaseAndIdentifierType(String identifier, IdentifierTypeEnum identifierType);

  void deleteByIdentifierIgnoreCase(String identifier);
}
