package gov.saip.applicationservice.common.controllers.applicationSnapShot;


import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationSnapShotDto.ApplicationSnapShotDropDownMenuResponseDto;
import gov.saip.applicationservice.common.dto.ApplicationSnapShotDto.ApplicationSnapShotDto;
import gov.saip.applicationservice.common.mapper.applicationSnapShot.ApplicationSnapShotMapper;
import gov.saip.applicationservice.common.model.ApplicationSnapShot;
import gov.saip.applicationservice.common.service.applicationSnapShot.ApplicationSnapShotService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/application-snapshot", "/internal-calling/application-snapshot"})
@RequiredArgsConstructor
@Slf4j
public class ApplicationSnapShotController extends BaseController<ApplicationSnapShot, ApplicationSnapShotDto, Long> {
    private final ApplicationSnapShotService applicationSnapShotService;
    private final ApplicationSnapShotMapper applicationSnapShotMapper;
    @Override
    protected BaseService<ApplicationSnapShot, Long> getService() {
        return  applicationSnapShotService;
    }

    @Override
    protected BaseMapper<ApplicationSnapShot, ApplicationSnapShotDto> getMapper() {
        return applicationSnapShotMapper;
    }
  @GetMapping("/{appId}/version/{versionNumber}")
      ApiResponse<ApplicationSnapShotDto> getApplicationByVersionNumber(@PathVariable(name="appId") String appId , @PathVariable(name ="versionNumber") Integer versionNumber) {
      return ApiResponse.ok(applicationSnapShotService.getApplicationByVersionNumber(versionNumber,Long.valueOf(appId)));
  }

    @GetMapping("/{appId}/versions")
    ApiResponse<List<ApplicationSnapShotDropDownMenuResponseDto>> getApplicationVersionNumberAndCreatedDateHijri(@PathVariable(name="appId") String appId ) {
        return ApiResponse.ok(applicationSnapShotService.getApplicationVersionNumberAndCreatedDateHijri(Long.valueOf(appId)));
    }

   @PostMapping("/take")
    ApiResponse<String> getApplicationVersionNumberAndCreatedDateHijri(@RequestBody ApplicationSnapShotDto dto ) {
         applicationSnapShotService.takeSnapShot(dto);
       return ApiResponse.ok("Version Saved Successfully");
  }

}
