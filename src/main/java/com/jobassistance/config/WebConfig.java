package com.jobassistance.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Web設定クラス
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * CORS設定を追加する
     * すべてのオリジンからのAPIアクセスを許可する
     * 
     * @param registry CORSレジストリ
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOriginPatterns("*") // 完全公開モード: すべてのオリジンを許可（allowCredentialsと併用可能）
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
                .allowedHeaders("*")
                .allowCredentials(true)
                .maxAge(86400); // 24時間
    }
}
