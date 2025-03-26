package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PublicationSchedulingConfigCreateDto;
import gov.saip.applicationservice.common.dto.PublicationSchedulingConfigViewDto;
import gov.saip.applicationservice.common.enums.ApplicationCategoryEnum;
import gov.saip.applicationservice.common.mapper.PublicationSchedulingConfigMapper;
import gov.saip.applicationservice.common.model.PublicationSchedulingConfig;
import gov.saip.applicationservice.common.service.PublicationSchedulingConfigService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping(value = {"/kc/publication-scheduling-config", "/internal-calling/publication-scheduling-config"})
@RequiredArgsConstructor
@Slf4j
public class PublicationSchedulingConfigController {
    private final PublicationSchedulingConfigService publicationSchedulingConfigService;

    private final PublicationSchedulingConfigMapper publicationSchedulingConfigMapper;

    @GetMapping("/{application-category-saip-code}")
    public ApiResponse<PublicationSchedulingConfigViewDto> getByApplicationCategory(@PathVariable("application-category-saip-code")
                                                                                    ApplicationCategoryEnum applicationCategorySaipCode) {
        PublicationSchedulingConfig config =
                publicationSchedulingConfigService.findByApplicationCategorySaipCode(applicationCategorySaipCode.name());
        return ApiResponse.ok(publicationSchedulingConfigMapper.map(config));
    }

    @PutMapping
    public ApiResponse<Void> createOrUpdate(@Valid @RequestBody PublicationSchedulingConfigCreateDto dto) {
        publicationSchedulingConfigService.createOrReplace(publicationSchedulingConfigMapper.fromCreateDto(dto));
        return ApiResponse.ok();
    }
}
