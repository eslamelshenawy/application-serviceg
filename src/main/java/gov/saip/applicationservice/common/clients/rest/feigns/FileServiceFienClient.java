package gov.saip.applicationservice.common.clients.rest.feigns;

import gov.saip.applicationservice.common.dto.FileServiceResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@FeignClient(value = "file-service", url = "${nuxeo.base-url}")
public interface FileServiceFienClient {
    @PostMapping(value = "/uploadAsFile/", consumes = "multipart/form-data")
    FileServiceResponseDto uploadFile(@RequestHeader("username") String username,
                                      @RequestPart(value = "file") MultipartFile file);


    @GetMapping(value = "/previewFile/{id}/IPRs4Patent/")
    ByteArrayResource getFile(@PathVariable(name = "id") String id);


}