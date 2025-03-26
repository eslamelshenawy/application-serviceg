package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.StartProcessResponseDto;
import gov.saip.applicationservice.common.model.ApplicationInfo;
import gov.saip.applicationservice.common.repository.ApplicationInfoRepository;
import gov.saip.applicationservice.modules.common.service.BaseApplicationInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("se/helper")
public class HelperController {
    @Autowired
    private BaseApplicationInfoService baseApplicationInfoService;

    @Autowired
    private ApplicationInfoRepository applicationInfoRepository;

    @GetMapping("re-start-process/{id}")
    public ApiResponse reStartProcessByAppid(@PathVariable long id) {
        // this service for the application TRADEMARK
        // if the application_info with (status NEW && has application_number) and does not create process instance
        try {
            Optional<ApplicationInfo> byId = applicationInfoRepository.findById(id);
            if (!byId.isPresent()) {
                throw new Exception("Not found");
            }
            StartProcessResponseDto startProcessResponseDto = baseApplicationInfoService.startProcessConfig(byId.get());
            baseApplicationInfoService.updateApplicationWithProcessRequestId(startProcessResponseDto, byId.get().getId());
            return ApiResponse.builder().payload(startProcessResponseDto).build();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }



}
