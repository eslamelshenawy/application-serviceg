package gov.saip.applicationservice.util.stubs;

import com.github.tomakehurst.wiremock.client.WireMock;
import gov.saip.applicationservice.common.dto.*;
import gov.saip.applicationservice.common.enums.CustomerConfigParameterEnum;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.stream.Collectors;

import static com.github.tomakehurst.wiremock.client.WireMock.equalToJson;

public interface CustomerServiceStubs extends Stubs {
    default void stubGetCustomerConfigParameter(CustomerConfigParameterEnum configParameter, String value) {
        String url = "/internal-calling/config/{name}".replace("{name}", configParameter.name());
        stub(WireMock.get(url), HttpStatus.OK, ApiResponse.ok(ConfigParameterDto.builder().name(configParameter.name()).value(value).build()));
    }

    default void stubGetUserColleagues(Long userId, List<Long> responsePayload) {
        String url = "/internal-calling/users/{userId}/colleagues".replace("{userId}", userId.toString());
        stub(WireMock.get(url), HttpStatus.OK, ApiResponse.ok(responsePayload));
    }

    default void stubGetCustomerIdByUserId(Long userId, Long responsePayload) {
        String url = "/internal-calling/users/{userId}/customer-id".replace("{userId}", userId.toString());
        stub(WireMock.get(url), HttpStatus.OK, ApiResponse.ok(responsePayload));
    }

    default void stubGetCustomerCodeByUserId(Long userId, String responsePayload) {
        String url = "/internal-calling/users/{userId}/customer-code".replace("{userId}", userId.toString());
        stub(WireMock.get(url), HttpStatus.OK, ApiResponse.ok(responsePayload));
    }

    default void stubGetCustomerByListOfCode(List<String> codes) {
        String url = "/internal-calling/customers/list/by-code";
        stub(WireMock.post(url).withRequestBody(equalToJson(serialize(CustomerCodeListDto.builder().customerCode(codes).build()))), HttpStatus.OK, ApiResponse.ok(List.of()));
    }


}
