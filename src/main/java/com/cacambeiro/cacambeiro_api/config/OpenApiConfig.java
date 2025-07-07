package com.cacambeiro.cacambeiro_api.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API CollectExpressBd - Gerenciamento de Caçambas")
                        .version("1.0.0")
                        .description("Esta API permite o gerenciamento de caçambeiros, usuários e caçambas, incluindo funcionalidades de aluguel e capacidades de armazenamento. Desenvolvida para simular as funcionalidades de um sistema de gestão de caçambas.")
                        .termsOfService("http://swagger.io/terms/")
                        .contact(new Contact()
                                .name("Anna Carolina De Azevedo Leite, "
                                		+ "Gabriel Andrade Ferreira,"
                                		+ "Giulia Caroline Claro, "
                                		+ "Leonardo Dias dos Santos, "
                                		+ "Pedro Henrique Batista,"
                                		+ "Vitória Moreno Tomazeli")
                                .url("https://github.com/seu-usuario/seu-repositorio")
                                )
                        .license(new License()
                                .name("Apache 2.0")
                                .url("github.com/annacarolinaa"
                                		+ "github.com/biel388"
                                		+ "github.com/GiuCaroline"
                                		+ "github.com/Leo-Santoss"
                                		+ "github.com/pedrosantos664"
                                		+ "github.com/vitomazeli")));
    }
}