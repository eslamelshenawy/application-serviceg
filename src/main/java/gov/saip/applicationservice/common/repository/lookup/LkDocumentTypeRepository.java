package gov.saip.applicationservice.common.repository.lookup;

import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.LkDocumentType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface LkDocumentTypeRepository extends BaseRepository<LkDocumentType, Long> {

    @Query("""
            select t from LkDocumentType t where t.name = :name or t.code = :name
            """)
    LkDocumentType findByNameOrCode(@Param("name") String name);
}
