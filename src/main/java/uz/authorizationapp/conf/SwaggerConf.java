package uz.authorizationapp.conf;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.annotations.security.SecuritySchemes;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;

@Configuration
@OpenAPIDefinition(info = @Info(title = "Online Bozor", version = "1.0",
        description = "Register,login,logout",
        contact = @Contact(name = "Abduvohid"))
)

@SecuritySchemes({
        @SecurityScheme(name = "bearerToken", type = SecuritySchemeType.HTTP,
                scheme = "bearer", bearerFormat = "UUID")
})
public class SwaggerConf {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .addSecurityItem(new io.swagger.v3.oas.models.security.SecurityRequirement().
                        addList("Bearer Authentication"))
                .components(new Components().addSecuritySchemes
                        ("Bearer Authentication", createAPIKeyScheme()))
//                .servers(Arrays.asList( new Server().url("http://test.bozor-online.uz")))
                .info(new io.swagger.v3.oas.models.info.Info().title("My REST API")
                        .description("Some custom description of API.")
                        .version("1.0")
                        .license(new License().name("License of API")
                                .url("API license URL")));
    }
    private io.swagger.v3.oas.models.security.SecurityScheme createAPIKeyScheme() {
        return new io.swagger.v3.oas.models.security.SecurityScheme().type(io.swagger.v3.oas.models.security.SecurityScheme.Type.HTTP)
                .bearerFormat("UUID")
                .scheme("bearer");
    }
}
