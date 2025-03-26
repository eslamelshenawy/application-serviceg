package gov.saip.applicationservice.common.clients.rest.feigns;


import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ConfigParameterDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.Map;

@FeignClient(name = "customer-codeConfigParameter", url = "${client.feign.customer}")
public interface CustomerConfigParameterClient {
    @GetMapping("/internal-calling/config/{name}")
    ApiResponse<ConfigParameterDto> getConfig(@PathVariable(name = "name") String name);

    @GetMapping("/internal-calling/configs")
    ApiResponse<Map<String, String>> getConfigs(@RequestBody List<String> names);

}
