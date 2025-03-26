package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.LkCertificateTypeDto;
import gov.saip.applicationservice.common.model.LkCertificateType;
import gov.saip.applicationservice.common.service.LkCertificateTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(value = {"/kc/certificate-type", "/internal-calling/certificate-type"})
@RequiredArgsConstructor
@Slf4j
public class LkCertificateTypeController extends BaseLkpController<LkCertificateType, LkCertificateTypeDto, Integer> {

    private final LkCertificateTypeService lkCertificateTypeService;

    @GetMapping("/specific-category")
    public ApiResponse<List<LkCertificateTypeDto>> findCertificateTypesByCategoryId(@RequestParam(value = "category-id" , required = false) Long categoryId){
        return ApiResponse.ok(lkCertificateTypeService.findCertificateTypesByCategoryId(categoryId));
    }

}
