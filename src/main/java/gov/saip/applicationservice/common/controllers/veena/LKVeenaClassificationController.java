package gov.saip.applicationservice.common.controllers.veena;

import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.veena.LKVeenaClassificationDto;
import gov.saip.applicationservice.common.model.veena.LKVeenaClassification;
import gov.saip.applicationservice.common.service.veena.LKVeenaClassificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/kc/veena-classification", "/internal-calling/veena-classification"})
@RequiredArgsConstructor
@Slf4j
public class LKVeenaClassificationController extends BaseLkpController<LKVeenaClassification, LKVeenaClassificationDto, Long> {

    @Autowired
    private LKVeenaClassificationService veenaClassificationService;

    @GetMapping("/category/page")
    public ApiResponse<PaginationDto> getVeenaClassificationsByCategory(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                                                        @RequestParam(value = "limit", defaultValue = "10") Integer limit,
                                                                        @RequestParam(value = "sortableColumn", required = false, defaultValue = "id") String sortableColumn,
                                                                        @RequestParam(value = "search", required = false, defaultValue = "") String search,
                                                                        @RequestParam(value = "categoryId", required = false) Long categoryId) {
        return ApiResponse.ok(veenaClassificationService.getAllVeenaClassificationsBySearch(page, limit, sortableColumn, search, categoryId));
    }

}
