package by.cizmar.internal.learn_rabbit_mq.config;

import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

@Configuration
@EnableWebMvc
@RequiredArgsConstructor
@SecurityScheme(type = SecuritySchemeType.HTTP, name = "basicAuth", scheme = "basic")
public class SpringDocOpenApiConfig extends WebMvcConfigurationSupport {
    @Bean
    public OpenAPI openAPIBean() {
        return new OpenAPI().components(getComponent()).info(getInfo());
    }

    private Info getInfo() {
        return new Info().title("Learn RabbitMQ")
                .description("Learn RabbitMQ - Pet Project Web API");
    }

    private Components getComponent() {
        return new Components();
    }
}
