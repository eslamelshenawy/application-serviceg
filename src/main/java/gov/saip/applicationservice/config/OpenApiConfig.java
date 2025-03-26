package gov.saip.applicationservice.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
@OpenAPIDefinition(servers = { @Server(url = "${openapi.server.url}", description = "Server URL")})
public class OpenApiConfig {

	private static final String SECURITY_SCHEME_NAME = "Bearer oAuth Token";
    private static final String COOKIE_SECURITY_SCHEME_NAME = "CookieAuth";


    @Value("${openapi.contact.url}")
    private String openApiContactUrl;

    @Bean
    public OpenAPI customOpenAPI(@Value("${spring.application.name}") String title, @Value("${spring.application.name}") String description) {
    	String contactEmail = "help@wscdev.net";
    	String version = "0.0.0.1"; 
		return new OpenAPI().openapi("3.0.1")
				.addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME, Arrays.asList("read", "write")))
				.components(
						new Components()
						.addSecuritySchemes(SECURITY_SCHEME_NAME,
								new SecurityScheme().name(SECURITY_SCHEME_NAME)
								.type(SecurityScheme.Type.HTTP)
								.scheme("bearer")
								.bearerFormat("JWT")
							)
                                .addSecuritySchemes(COOKIE_SECURITY_SCHEME_NAME,
                                        new SecurityScheme().name(COOKIE_SECURITY_SCHEME_NAME)
                                                .type(SecurityScheme.Type.APIKEY)
                                                .in(SecurityScheme.In.COOKIE)
                                                .name("SESSION")
                                )
						)
				.info(new Info()
						.title(title)
                        .description(description)
                        .version(version)
                        .termsOfService("Terms of service")
                        .license(getLicense())
                        .contact(getContact(contactEmail,title)));
    }

    private Contact getContact(String contactEmail, String title) {
        Contact contact = new Contact();
        contact.setEmail(contactEmail);
        contact.setName(title);
        contact.setUrl(openApiContactUrl);
        contact.setExtensions(Collections.emptyMap());
        return contact;
    }

    private License getLicense() {
        License license = new License();
        license.setName("Apache License, Version 2.0");
        license.setUrl("http://www.apache.org/licenses/LICENSE-2.0");
        license.setExtensions(Collections.emptyMap());
        return license;
    }
}