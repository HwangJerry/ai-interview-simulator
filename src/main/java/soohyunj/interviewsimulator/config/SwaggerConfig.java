package soohyunj.interviewsimulator.config;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.servers.Server;
import jakarta.servlet.ServletContext;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;

import java.util.List;

@Configuration
public class SwaggerConfig {
    @Bean
    public OpenAPI openAPI(ServletContext servletContext) {
        String contextPath = servletContext.getContextPath();
        Server server = new Server().url(contextPath);
        return new OpenAPI()
                .servers(List.of(server)) // API server url 표시
                .info(info()) // API 메타 정보 표시
                .addSecurityItem(securityItem())
                .components(new Components()
                        .addSecuritySchemes("Authorization(accessToken)", securityScheme())); // API 스펙의 다른 구성 요소들 표시

    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("ai-interview-simulator-client")
                .pathsToMatch("/**")
                .build();
    }

//    @Bean
//    public GroupedOpenApi adminApi() {
//        return GroupedOpenApi.builder()
//                .group("ai-interview-simulator-admin")
//                .pathsToMatch("/admin/**")
//                .build();
//    }

    private Info info() {
        return new Info()
                .version("v1")
                .title("AI 인터뷰 시뮬레이터 API Specification")
                .description("클라이언트 API 명세서")
                .license(license())
                .contact(contact());

    }
    private License license() {
        License license = new License();
        license.setUrl("https://github.com/HwangJerry");
        license.setName("licensed by github repository");
        return license;
    }
    private Contact contact() {
        return new Contact()
                .name("황제철")
                .email("jerryhwang082@gmail.com");
    }

    private SecurityRequirement securityItem() {
        SecurityRequirement securityItem = new SecurityRequirement();
        securityItem.addList("Authorization(accessToken)");
        return securityItem;
    }

    private SecurityScheme securityScheme() {
        return new SecurityScheme()
                .type(SecurityScheme.Type.HTTP)
                .scheme("bearer")
                .bearerFormat("JWT")
                .in(SecurityScheme.In.HEADER)
                .name(HttpHeaders.AUTHORIZATION);
    }
}
