package br.com.customer.orders.infrastructure.configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfiguration {
    private final String version;
    private final String title;
    private final String description;
    private final String termsOfService;
    private final String licenseName;
    private final String licenseUrl;

    public SwaggerConfiguration(
            @Value("${springdoc.default.version}") String version,
            @Value("${springdoc.default.title}") String title,
            @Value("${springdoc.default.description}") String description,
            @Value("${springdoc.default.terms-of-service}") String termsOfService,
            @Value("${springdoc.default.license.name}") String licenseName,
            @Value("${springdoc.default.license.url}") String licenseUrl
    ) {
        this.version = version;
        this.title = title;
        this.description = description;
        this.termsOfService = termsOfService;
        this.licenseName = licenseName;
        this.licenseUrl = licenseUrl;
    }

    @Bean
    public License getLicense() {
        return new License()
                .name(licenseName)
                .url(licenseUrl);
    }

    @Bean
    public Info getInfo(License license) {
        return new Info()
                .title(title)
                .description(description)
                .termsOfService(termsOfService)
                .version(version)
                .license(license);
    }

    @Bean
    public Components getComponents() {
        return new Components();
    }

    @Bean
    public OpenAPI getOpenAPI(Info info, Components components) {
        return new OpenAPI()
                .components(components)
                .info(info);
    }
}
