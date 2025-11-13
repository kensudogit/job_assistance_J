package com.jobassistance.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

/**
 * セキュリティ設定クラス
 * 一時的に認証を無効化
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    /**
     * セキュリティフィルターチェーンを設定する
     * CSRF保護を無効化し、すべてのリクエストを許可する
     * 
     * @param http HttpSecurityオブジェクト
     * @return 設定されたSecurityFilterChain
     * @throws Exception 設定エラー
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
        return http.build();
    }
}

