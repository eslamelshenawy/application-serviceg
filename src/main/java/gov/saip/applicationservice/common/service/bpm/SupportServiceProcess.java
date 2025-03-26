package gov.saip.applicationservice.common.service.bpm;

import gov.saip.applicationservice.common.dto.StartProcessResponseDto;
import gov.saip.applicationservice.common.dto.bpm.StartProcessDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;

public interface SupportServiceProcess {

    void starSupportServiceProcess(StartProcessDto processDto);
    StartProcessResponseDto startProcess(StartProcessDto processDto);
    StartProcessResponseDto startProcessConfig(ApplicationInfo applicationInfo);
    StartProcessResponseDto starProcessApplication(ApplicationInfo applicationInfo, String processName, String requestType);


}
