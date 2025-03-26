package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.LkFastTrackExaminationTargetAreaDto;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.mapper.LkFastTrackExaminationTargetAreaMapper;
import gov.saip.applicationservice.common.model.LkFastTrackExaminationTargetArea;
import gov.saip.applicationservice.common.service.lookup.LkFastTrackExaminationTargetAreaService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(value = {"/kc/fast-track-examination-target-areas", "/internal-calling/fast-track-examination-target-areas"})

public class LkFastTrackExaminationTargetAreaController extends BaseController<LkFastTrackExaminationTargetArea, LkFastTrackExaminationTargetAreaDto, Long> {


    private final LkFastTrackExaminationTargetAreaService lkFastTrackExaminationTargetAreaService;
    private final LkFastTrackExaminationTargetAreaMapper lkFastTrackExaminationTargetAreaMapper;
    @Override
    protected BaseService<LkFastTrackExaminationTargetArea, Long> getService() {
        return  lkFastTrackExaminationTargetAreaService;
    }

    @Override
    protected BaseMapper<LkFastTrackExaminationTargetArea, LkFastTrackExaminationTargetAreaDto> getMapper() {
        return lkFastTrackExaminationTargetAreaMapper;
    }


    @GetMapping("/category/page")
    public ApiResponse<PaginationDto> getAllPaginatedFastTrackExaminationAreaWithSearch(@RequestParam(required = false, defaultValue = "0") int page,
                                                                                        @RequestParam(required = false, defaultValue = "10") int limit,
                                                                                        @RequestParam(value = "search" , required = false) String search){
        return ApiResponse.ok(lkFastTrackExaminationTargetAreaService.getAllPaginatedFastTrackExaminationAreaWithSearch(page , limit ,search));
    }


}
