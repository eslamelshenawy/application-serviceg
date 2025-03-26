package gov.saip.applicationservice.common.clients.rest.feigns;


import gov.saip.applicationservice.common.dto.IpSearchDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name = "ipsearch-caller", url = "${client.feign.ipsearch}")
public interface IpsearchFeignClient {

    @GetMapping("{saipDocId}/bookmarks")
    IpSearchDto getSimilarApplications(@PathVariable(name = "saipDocId") String saipDocId
                                          ,@RequestHeader("API-KEY") String apiKey);

}
