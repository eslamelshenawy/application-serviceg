package gov.saip.applicationservice.config;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OpenApiCustomiser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public GroupedOpenApi operationApi() {
        String[] packagesToScan = {"gov.saip.applicationservice.common.controllers"};
        return GroupedOpenApi.builder().addOpenApiCustomiser(removeSecurityForPbPaths()).group("Common APIs")
                .packagesToScan(packagesToScan)
                .pathsToExclude("/internal-calling/**")
                .build();
    }
    private OpenApiCustomiser removeSecurityForPbPaths() {
        return openApi -> {
            openApi.getPaths().forEach((path, pathItem) -> {
                if (path.startsWith("/pb/")) {
                    pathItem.readOperations().forEach(operation -> operation.setSecurity(Collections.emptyList()));
                }
            });
        };
    }

}