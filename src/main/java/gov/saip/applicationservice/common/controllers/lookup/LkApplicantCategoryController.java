package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.lookup.LkApplicantCategoryDto;
import gov.saip.applicationservice.common.mapper.lookup.LkApplicantCategoryMapper;
import gov.saip.applicationservice.common.model.LkApplicantCategory;
import gov.saip.applicationservice.common.service.lookup.LkApplicantCategoryService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/applicant-category", "/internal-calling/applicant-category"})
@RequiredArgsConstructor
@Slf4j
public class LkApplicantCategoryController extends BaseController<LkApplicantCategory, LkApplicantCategoryDto, Long> {
    private final LkApplicantCategoryService lkApplicantCategoryService;
    private final LkApplicantCategoryMapper lkApplicantCategoryMapper;
    @Override
    protected BaseService<LkApplicantCategory, Long> getService() {
        return  lkApplicantCategoryService;
    }

    @Override
    protected BaseMapper<LkApplicantCategory, LkApplicantCategoryDto> getMapper() {
        return lkApplicantCategoryMapper;
    }
}
