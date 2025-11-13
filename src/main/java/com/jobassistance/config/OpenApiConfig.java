package com.jobassistance.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * OpenAPI/Swagger設定クラス
 */
@Configuration
public class OpenApiConfig {

    /**
     * OpenAPI設定を作成する
     * Swagger UIでAPIドキュメントを表示するための設定
     * 
     * @return OpenAPI設定オブジェクト
     */
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Job Assistance System API")
                        .version("1.0.0")
                        .description("外国人向け就労支援システムのREST API")
                        .contact(new Contact()
                                .name("Job Assistance System")))
                .servers(List.of(
                        new Server().url("http://localhost:5000").description("開発環境"),
                        new Server().url("https://api.example.com").description("本番環境")));
    }
}
