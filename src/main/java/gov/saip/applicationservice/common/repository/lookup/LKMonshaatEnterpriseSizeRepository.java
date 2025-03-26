package gov.saip.applicationservice.common.repository.lookup;


import gov.saip.applicationservice.base.repository.BaseLkRepository;
import gov.saip.applicationservice.base.repository.BaseRepository;
import gov.saip.applicationservice.common.model.LKMonshaatEnterpriseSize;
import org.springframework.stereotype.Repository;

import javax.swing.text.html.Option;
import java.util.Optional;

@Repository
public interface LKMonshaatEnterpriseSizeRepository extends BaseLkRepository<LKMonshaatEnterpriseSize, Long> {

    Optional<LKMonshaatEnterpriseSize> findByCode(String code);
}
