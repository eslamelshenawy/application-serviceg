package gov.saip.applicationservice.common.clients.rest.feigns;



import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.soap.RequestEnterpriseSize;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "soap-translation-service", url = "${soap.translation.url}")
public interface SoapTranslationFeignClient {


    @PostMapping("/internal-calling/enterprise-size")
    ApiResponse<String> getEnterpriseSize(@RequestBody RequestEnterpriseSize requestEnterpriseSize );
}

