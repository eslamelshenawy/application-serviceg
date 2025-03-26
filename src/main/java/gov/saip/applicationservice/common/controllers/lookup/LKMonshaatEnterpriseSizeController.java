package gov.saip.applicationservice.common.controllers.lookup;

import gov.saip.applicationservice.base.controller.BaseController;
import gov.saip.applicationservice.base.controller.BaseLkpController;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.lookup.LKMonshaatEnterpriseSizeDto;
import gov.saip.applicationservice.common.model.LKMonshaatEnterpriseSize;
import gov.saip.applicationservice.common.service.lookup.LKMonshaatEnterpriseSizeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequiredArgsConstructor
@RequestMapping(value={"/kc/enterprise-size" , "/internal-calling/enterprise-size"})
public class LKMonshaatEnterpriseSizeController extends BaseLkpController<LKMonshaatEnterpriseSize, LKMonshaatEnterpriseSizeDto, Long> {
private final LKMonshaatEnterpriseSizeService lkMonshaatEnterpriseSizeService;

@GetMapping("/application-save")
    ApiResponse<String> saveOrganizationSizeOnApplication(@RequestParam(value="appId")Long appId,@RequestParam(value="customerCode")String customerCode){

    return ApiResponse.ok(lkMonshaatEnterpriseSizeService.saveApplicationForOrganizationSize(appId,customerCode));
}


}
