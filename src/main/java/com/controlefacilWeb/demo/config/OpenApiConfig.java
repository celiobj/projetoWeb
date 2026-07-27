package com.controlefacilWeb.demo.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuração do Swagger / OpenAPI 3.
 * Acesse a documentação em: http://localhost:8080/swagger-ui/index.html
 */
@Configuration
public class OpenApiConfig {

    private static final String SECURITY_SCHEME_NAME = "bearerAuth";

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("ControleFácil Web — API")
                        .description("API REST do sistema ControleFácil Web. " +
                                "Autentique-se em **POST /api/auth/login** e clique em **Authorize** " +
                                "informando o token recebido no formato `Bearer <token>`.")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("ControleFácil")
                                .email("")))
                // Esquema de autenticação JWT via header Authorization
                .addSecurityItem(new SecurityRequirement().addList(SECURITY_SCHEME_NAME))
                .components(new Components()
                        .addSecuritySchemes(SECURITY_SCHEME_NAME,
                                new SecurityScheme()
                                        .name(SECURITY_SCHEME_NAME)
                                        .type(SecurityScheme.Type.HTTP)
                                        .scheme("bearer")
                                        .bearerFormat("JWT")
                                        .description("Informe o token JWT obtido em POST /api/auth/login")));
    }
}
