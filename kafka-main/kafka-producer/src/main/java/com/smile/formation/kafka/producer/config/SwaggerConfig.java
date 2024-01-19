package com.smile.formation.kafka.producer.config;

import io.swagger.v3.oas.annotations.enums.ParameterIn;
//import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.Parameter;
//import io.swagger.v3.oas.models.security.SecurityRequirement;
//import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import lombok.extern.slf4j.Slf4j;

import org.springdoc.core.GroupedOpenApi;
import org.springdoc.core.customizers.OperationCustomizer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.web.method.HandlerMethod;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

@Configuration
@PropertySource("classpath:swagger.properties")
@Slf4j
public class SwaggerConfig {

    @Value("${swagger.doc.title}")
    private String title;

    @Value("${swagger.doc.description}")
    private String description;

    @Value("${swagger.doc.api.group}")
    private String groupName;

    @Value("${swagger.doc.version}")
    private String version;

    @Value("${swagger.doc.termServiceUrl}")
    private String termsOfServiceUrl;

    @Value("${swagger.doc.contact.name}")
    private String contactName;

    @Value("${swagger.doc.contact.url}")
    private String contactUrl;

    @Value("${swagger.doc.contact.email}")
    private String contactEmail;

    @Value("${swagger.doc.server.url}")
    private String serverUrl;

    @PostConstruct
    public void log() {
    	log.info("Swagger config loaded with server url {}", serverUrl);
    }
    
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
            .info(new Info().title(title)
                .description(description)
                .version(version)
                .license(new License().name("Apache 2.0").url("http://springdoc.org"))
                .contact(new Contact().email(contactEmail).name(contactName).url(contactUrl)))
            .externalDocs(new ExternalDocumentation().description(description).url(contactUrl))
            .servers(apiServers())
//            .components(
//            		new Components().addSecuritySchemes("Authentification",
//                new SecurityScheme()
//                    .type(SecurityScheme.Type.HTTP)
//                    .scheme("bearer")
//                    .bearerFormat("JWT"))
//            )
//            .addSecurityItem(new SecurityRequirement().addList("Authentification"))
            .servers(apiServers());
    }

    @Bean
    public OperationCustomizer customGlobalHeaders() {
        return (Operation operation, HandlerMethod handlerMethod) -> {
            Parameter sourceHeader = new Parameter()
                .in(ParameterIn.HEADER.toString())
                .required(true)
                .schema(new StringSchema()
                    ._default("swagger")
                    .readOnly(true))
                .name("Source")
                .description("Source header").required(true);
            operation.addParametersItem(sourceHeader);

            return operation;
        };
    }

    @Bean
    public GroupedOpenApi producerGroup() {
        String[] paths = {"/**"};
        return GroupedOpenApi.builder().group("producer").pathsToMatch(paths)
            .addOperationCustomizer(customGlobalHeaders())
            .build();
    }

    private List<Server> apiServers() {
        List<Server> servers = new ArrayList<>();
        Server server = new Server();
        server.setUrl(serverUrl);
        servers.add(server);
        return servers;
    }
}
