package gov.saip.applicationservice.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.nimbusds.jwt.JWT;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.PlainJWT;
import gov.saip.applicationservice.ApplicationServiceApplication;
import gov.saip.applicationservice.security.TokenAttributesEnum;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpHeaders;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.RequestPostProcessor;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.output.Slf4jLogConsumer;

import javax.persistence.EntityManager;
import java.text.ParseException;
import java.time.Clock;
import java.util.Map;

// TODO[Fawzy] shouldn't need local profile here, override needed properties in -test.yaml
@ActiveProfiles({"local", "test"}) // Activate local profile to override dev-environment specific settings
@SpringBootTest(classes = {ApplicationServiceApplication.class, TestRedisConfig.class},webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
@ComponentScan // Scan for @TestComponent annotated classes
@AutoConfigureMockMvc
@Tag("integration")
@ExtendWith({DatabaseCleanupExtension.class, GlobalWireMockExtension.class})
@Slf4j
public abstract class BaseIntegrationTest {
    // TODO[Fawzy - from Nour] create base layer automatic tests

    @Getter
    @Autowired
    protected ObjectMapper objectMapper;

    @Getter
    @Autowired
    protected MockMvc mockMvc;

    @Getter
    @Autowired
    protected EntityManager entityManager;

    @Autowired
    protected TestLkFactory testLkFactory;

    public String serialize(Object o) {
        try {
            return objectMapper.writeValueAsString(o);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> T deserialize(ResultActions result, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(result.andReturn().getResponse().getContentAsString(), typeReference);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static RequestPostProcessor jwt(Map<String, Object> claims) {
        return request -> {
            try {
                JWT jwt = new PlainJWT(
                        JWTClaimsSet.parse(claims)
                );
                request.addHeader(HttpHeaders.AUTHORIZATION, "Bearer " + jwt.serialize());
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            return request;
        };
    }

    public static RequestPostProcessor defaultJwt() {
        return jwt(Map.of(
                TokenAttributesEnum.USER_EMAIL.value, "mahmoud@gmail.com",
                TokenAttributesEnum.USER_SYSTEM_ID.value, "mahmoud123",
                TokenAttributesEnum.PREFERRED_USER_NAME.value, "Mahmoud Fawzy",
                TokenAttributesEnum.USER_NAME.value, "sabry"
        ));
    }

    // Create PostgreSQL database container
    private static final PostgreSQLContainer<?> POSTGRES_CONTAINER;

    private static final String DB_USER = "test-user";

    public static final String DB_CONTEXT = "123";

    public static final String DB_NAME = "TestDB";

    static {
        POSTGRES_CONTAINER = new PostgreSQLContainer<>("postgres:13.11")
                .withLogConsumer(new Slf4jLogConsumer(log))
                .withUsername(DB_USER)
                .withPassword(DB_CONTEXT)
                .withDatabaseName(DB_NAME);
        POSTGRES_CONTAINER.start();
    }

    @DynamicPropertySource
    static void dynamicProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", POSTGRES_CONTAINER::getJdbcUrl);
        registry.add("client.feign.customer", GlobalWireMockExtension.getWireMock()::baseUrl);
        registry.add("client.feign.e-filing-bpm", GlobalWireMockExtension.getWireMock()::baseUrl);
    }

    @TestConfiguration
    static class TestClock {
        @Bean
        @Primary
        public Clock testClock(){
            return TestUtils.WEDNESDAY_2023_06_14_AT_01_00_CLOCK;
        }
    }
}

