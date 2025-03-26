package gov.saip.applicationservice.common.clients.rest.feigns;

import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.lookup.LkFeeCostDto;
import gov.saip.applicationservice.common.dto.payment.ApplicationBillLightDTO;
import gov.saip.applicationservice.common.dto.payment.BillCreationDto;
import gov.saip.applicationservice.common.dto.payment.BillResponseModel;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "payment-fee-cost-service", url = "${client.feign.payment}")
public interface PaymentFeeCostFeignClient {

    @GetMapping("/internal-calling/fee-cost/codes")
    List<LkFeeCostDto> findCost(@RequestParam("code") String code
            , @RequestParam("applicantCategorySaipCode") String applicantCategorySaipCode
            , @RequestParam("applicationCategorySaipCode") String applicationCategorySaipCode);


   @PostMapping("/internal-calling/api/bill/create")
   ApiResponse<BillResponseModel> createBill(@RequestBody BillCreationDto billCreationDto);

    @GetMapping("/internal-calling/application/{appId}/mainRequestType/{mainRequestType}")
    public ApiResponse<ApplicationBillLightDTO> getBillDetailsByApplicationIdAndMainRequestType(@PathVariable(name = "appId") Long appId, @PathVariable(name = "mainRequestType") String mainRequestType);

    @GetMapping("/internal-calling/api/last-bill-number")
    ApiResponse<String> findLastBillByRequestTypeAndAppOrServiceId(
            @RequestParam(name = "appId", required = false) Long appId,
            @RequestParam(name = "serviceId",required = false) Long serviceId,
            @RequestParam(name = "mainRequestType") String mainRequestType);


    @GetMapping("/internal-calling/api/bill-number-appId")
    ApiResponse<String> getBillNumberByAppId(@RequestParam(name = "appId", required = false) Long appId,@RequestParam(value = "certificateTypeCode", required = false) String certificateTypeCode );
}
