package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.PaginationDto;
import gov.saip.applicationservice.common.dto.SubClassificationDto;
import gov.saip.applicationservice.common.mapper.SubClassificationMapper;
import gov.saip.applicationservice.common.model.SubClassification;
import gov.saip.applicationservice.common.service.SubClassificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping(value = {"kc/sub-classification", "/internal-calling/sub-classification"})

@RequiredArgsConstructor
public class SubClassificationController extends BaseController<SubClassification, SubClassificationDto, Long> {

    private final SubClassificationService subClassificationService;
    private final SubClassificationMapper subClassificationMapper;

    @Override
    protected BaseService<SubClassification, Long> getService() {
        return  subClassificationService;
    }

    @Override
    protected BaseMapper<SubClassification, SubClassificationDto> getMapper() {
        return subClassificationMapper;
    }
    @GetMapping("/classification/{id}")
    public ApiResponse<PaginationDto> getCategories(
            @PathVariable("id") long categoryId,
            @RequestParam() long applicationId,
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false) String query,
            @RequestParam(required = false) Boolean isShortcut) {
        return ApiResponse.ok(subClassificationService.getAllSubClass(page, limit, query, isShortcut, applicationId, categoryId));
    }


    @GetMapping("/application/{id}/{tmoCustomerCode}")
    PaginationDto findSubClassificationByTrademarkId(
            @PathVariable(value = "id") Long applicationId,
            @PathVariable(value = "tmoCustomerCode") String tmoCustomerCode,
            @RequestParam(value = "subClassificationId", required = false) Long subClassificationId,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "page",required = false, defaultValue = "0") int page,
            @RequestParam(value = "limit",required = false, defaultValue = "10") int limit
    ){
        return subClassificationService.findSubClassificationByTrademarkId(page, limit, tmoCustomerCode, applicationId, subClassificationId, code);
    }



    @GetMapping("/get-by-classification")
    public ApiResponse<PaginationDto> getAllSubClassificationsByClassificationId(
            @RequestParam(required = false, defaultValue = "0") int page,
            @RequestParam(required = false, defaultValue = "10") int limit,
            @RequestParam(required = false) String query,
            @RequestParam(value = "classId") long classId) {

        PaginationDto result = subClassificationService.getAllSubClassificationsByClassificationId(page, limit, query, classId);
        return ApiResponse.ok(result);
    }


    @GetMapping("/revoke-product/{applicationId}")
    public ApiResponse<PaginationDto> revokeProductsSubClassification(
            @PathVariable long applicationId,
            @RequestParam(name = "classificationId", required = false) Long classificationId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int limit,
            @RequestParam(required = false) String query,
            @RequestParam(name = "basicNumber", required = false) Long basicNumber) {
        return ApiResponse.ok(subClassificationService.revokeProductsSubClassification(page, limit, classificationId, applicationId, query , basicNumber));
    }


}
