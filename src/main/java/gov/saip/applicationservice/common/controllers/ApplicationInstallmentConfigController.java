package gov.saip.applicationservice.common.controllers;


import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ApplicationInstallmentConfigDto;
import gov.saip.applicationservice.common.dto.installment.ApplicationInstallmentDto;
import gov.saip.applicationservice.common.mapper.ApplicationInstallmentConfigMapper;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallment;
import gov.saip.applicationservice.common.model.installment.ApplicationInstallmentConfig;
import gov.saip.applicationservice.common.service.installment.ApplicationInstallmentConfigService;
import gov.saip.applicationservice.common.service.installment.impl.ApplicationInstallmentConfigServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping( value = {"/kc/installment-config", "/internal-calling/installment-config"})
@RequiredArgsConstructor
public class ApplicationInstallmentConfigController extends BaseController<ApplicationInstallmentConfig, ApplicationInstallmentConfigDto, Long> {


    private final ApplicationInstallmentConfigService configService;
    private final ApplicationInstallmentConfigMapper configMapper;
    @Override
    protected BaseService<ApplicationInstallmentConfig, Long> getService() {
        return configService;
    }

    @Override
    protected BaseMapper<ApplicationInstallmentConfig, ApplicationInstallmentConfigDto> getMapper() {
        return configMapper;
    }

    @GetMapping("/category")
    public ApiResponse<ApplicationInstallmentConfigDto> findByApplicationCategory(@RequestParam(value = "category")String category){
        ApplicationInstallmentConfigDto configDto = configMapper.map(configService.findByApplicationCategory(category));
        return ApiResponse.ok(configDto);
    }



}
