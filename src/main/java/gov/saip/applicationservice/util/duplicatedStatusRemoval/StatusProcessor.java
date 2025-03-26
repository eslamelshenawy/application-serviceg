package gov.saip.applicationservice.util.duplicatedStatusRemoval;

import gov.saip.applicationservice.common.dto.lookup.LkApplicationStatusDto;
import gov.saip.applicationservice.common.enums.ApplicationStatusGroupEnum;

import java.util.List;

public interface StatusProcessor< T extends ApplicationStatusGroupEnum> {
    void process (List<LkApplicationStatusDto> status );
}
