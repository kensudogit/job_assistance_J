package com.jobassistance.service;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * パスワードエンコーダー設定クラス
 * パスワードのハッシュ化に使用するエンコーダーを提供
 */
@Configuration
public class PasswordEncoderConfig {

    /**
     * パスワードエンコーダーのBeanを定義する
     * BCryptアルゴリズムを使用したパスワードエンコーダーを返す
     *
     * @return BCryptPasswordEncoderインスタンス
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

