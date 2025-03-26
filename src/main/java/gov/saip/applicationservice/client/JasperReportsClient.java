package gov.saip.applicationservice.client;

import gov.saip.applicationservice.base.client.BaseClient;
import gov.saip.applicationservice.common.dto.reports.ReportRequestDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.io.IOException;

@FeignClient(name = "jasper-reports", url = "${client.feign.dashboard}/internal-calling/report")
public interface JasperReportsClient extends BaseClient<ReportRequestDto, Long> {
    @PostMapping("/pdf/{reportName}")
     ResponseEntity<ByteArrayResource> exportToPdf(@RequestBody ReportRequestDto dto , @PathVariable(name = "reportName") String reportName) throws IOException;
}
