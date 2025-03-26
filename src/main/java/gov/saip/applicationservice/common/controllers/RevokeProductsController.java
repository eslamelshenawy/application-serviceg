package gov.saip.applicationservice.common.controllers;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.mapper.BaseMapper;
import gov.saip.applicationservice.base.service.BaseService;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.RevokeProductsDto;
import gov.saip.applicationservice.common.mapper.RevokeProductsMapper;
import gov.saip.applicationservice.common.model.RevokeProducts;
import gov.saip.applicationservice.common.service.RevokeProductsService;
import gov.saip.applicationservice.util.SupportServiceValidator;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = {"/kc/support-service/revoke-products", "/internal-calling/support-service/revoke-products"})
@RequiredArgsConstructor
@Slf4j
public class RevokeProductsController extends BaseController<RevokeProducts, RevokeProductsDto, Long> {

    private final RevokeProductsService revokeProductsService;
    private final RevokeProductsMapper revokeProductsMapper;
    private final SupportServiceValidator supportServiceValidator;
    @Override
    protected BaseService<RevokeProducts, Long> getService() {
        return revokeProductsService;
    }

    @Override
    protected BaseMapper<RevokeProducts, RevokeProductsDto> getMapper() {
        return revokeProductsMapper;
    }

    @GetMapping("/service/{id}")
    public ApiResponse<RevokeProductsDto> getRevokeProductsByApplicationSupportServiceId(@PathVariable(name = "id") Long applicationSupportServiceId) {
        return ApiResponse.ok(revokeProductsService.findRevokeProductById(applicationSupportServiceId));
    }

    @DeleteMapping("/{id}/revokedProducts")
    public ApiResponse deleteApplicationRevokedProductsByRevokeProductId(@PathVariable(name = "id") Long revokeProductId) {
        revokeProductsService.deleteApplicationRevokedProductsByRevokeProductId(revokeProductId);
        return ApiResponse.ok();
    }
    @GetMapping("/{id}")
    @Operation(summary = "Find by id", description = "to retrieve element by id")
    @Override
    public ApiResponse<RevokeProductsDto> findById(@PathVariable(name = "id") Long id) {
        return ApiResponse.ok(revokeProductsService.findRevokeProductById(id));
    }

}
