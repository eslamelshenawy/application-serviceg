package gov.saip.applicationservice.common.service.lookup;

import gov.saip.applicationservice.base.service.BaseLkService;
import gov.saip.applicationservice.common.model.LKPublicationType;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public interface LKPublicationTypeService extends BaseLkService<LKPublicationType , Integer> {

    List<LKPublicationType> getPublicationTypes(Long id);
}
