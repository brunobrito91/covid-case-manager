package com.example.covidcasemanager.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    private final String projectName;
    private final String projectVersion;
    private final String projectDescription;

    public OpenApiConfig(@Value("${project.name}") String projectName,
                         @Value("${project.version}") String projectVersion,
                         @Value("${project.description}") String projectDescription) {
        this.projectName = projectName;
        this.projectVersion = projectVersion;
        this.projectDescription = projectDescription;
    }

    @Bean
    public OpenAPI customOpenApi() {
        final String securitySchemeName = "bearerAuth";

        return new OpenAPI()
                .addSecurityItem(new SecurityRequirement().addList(securitySchemeName))
                .components(new Components()
                        .addSecuritySchemes(
                                securitySchemeName,
                                new SecurityScheme()
                                        .name(securitySchemeName)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                        )
                )
                .info(new Info()
                        .title(projectName)
                        .description(projectDescription)
                        .version(projectVersion)
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")
                        )
                )
                .externalDocs(new ExternalDocumentation()
                        .description("Serasa Experian")
                        .url("https://www.serasaexperian.com.br/")
                );
    }
}
