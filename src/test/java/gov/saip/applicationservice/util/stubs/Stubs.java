package gov.saip.applicationservice.util.stubs;

import com.github.tomakehurst.wiremock.client.MappingBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import gov.saip.applicationservice.util.GlobalWireMockExtension;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

public interface Stubs {
    String serialize(Object o);

    default void stub(MappingBuilder mappingBuilder, HttpStatus status, Object response) {
        GlobalWireMockExtension.getWireMock().stubFor(
                mappingBuilder.willReturn(WireMock.aResponse()
                        .withStatus(status.value())
                        .withBody(serialize(response))
                        .withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                ));
    }
}
