package gov.saip.applicationservice.common.service.applicationSnapShot;

import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApplicationSnapShotDto.ApplicationSnapShotDropDownMenuResponseDto;
import gov.saip.applicationservice.common.dto.ApplicationSnapShotDto.ApplicationSnapShotDto;
import gov.saip.applicationservice.common.model.ApplicationSnapShot;

import java.util.List;

public interface ApplicationSnapShotService extends BaseService<ApplicationSnapShot, Long> {


    ApplicationSnapShotDto getApplicationByVersionNumber(Integer versionNumber, Long appId);
    List<ApplicationSnapShotDropDownMenuResponseDto> getApplicationVersionNumberAndCreatedDateHijri(Long appId);
    void takeSnapShot(ApplicationSnapShotDto dto);


}
