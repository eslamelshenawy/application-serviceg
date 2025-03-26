package gov.saip.applicationservice.common.clients.rest.feigns;



import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ListBodyDto;
import gov.saip.applicationservice.common.dto.customer.CountryDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;

@FeignClient(name = "customer", url = "${client.feign.customer}")
public interface CustomerClient {

    @GetMapping("/internal-calling/countries-by-ids")
    ApiResponse<List<CountryDto>> getCountriesByIds(@RequestParam("ids") List<Long> ids);

    @GetMapping("/internal-calling/countries/{id}")
    ApiResponse<CountryDto> getCountryById(@PathVariable(name = "id") Long id);

    @GetMapping ("/internal-calling/customer/customerCode")
    ApiResponse<Boolean> checkCustomerCodeExistsOnCustomer(@RequestParam(value = "customerCode") String customerCode);
    @GetMapping ("/internal-calling/customer/list/by-ids/{query}")
    public List<Long> getCustomersByIdsAndName(@RequestBody List<Long> ids,@PathVariable(value = "query") String query);

}
