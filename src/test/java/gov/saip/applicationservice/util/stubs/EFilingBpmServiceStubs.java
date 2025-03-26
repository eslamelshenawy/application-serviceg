package gov.saip.applicationservice.util.stubs;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.matching.UrlPattern;
import gov.saip.applicationservice.common.dto.ApiResponse;
import gov.saip.applicationservice.common.dto.ConfigParameterDto;
import gov.saip.applicationservice.common.dto.RequestTasksDto;
import gov.saip.applicationservice.common.enums.CustomerConfigParameterEnum;
import gov.saip.applicationservice.util.GlobalWireMockExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import java.util.List;
import java.util.stream.Collectors;

public interface EFilingBpmServiceStubs extends Stubs {
    default void stubGetTaskRowById(List<RequestTasksDto> responsePayload) {
        String url = "/internal-calling/v1/process/task\\?rowIds";
        // TODO[Sabry]

        UrlPattern urlPattern = WireMock.urlMatching(url + ".*");//        String queryParamValue = rowIds.stream().map(String::valueOf).collect(Collectors.joining(","));
        stub(WireMock.get(urlPattern), HttpStatus.OK, responsePayload);
    }
}
