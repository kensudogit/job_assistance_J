package com.jobassistance.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;

/**
 * Jackson設定クラス
 */
@Configuration
public class JacksonConfig {

    /**
     * ObjectMapperを設定する
     * JavaTimeModuleを登録して、LocalDateやLocalDateTimeを適切にシリアライズ/デシリアライズできるようにする
     * 
     * @param builder Jackson2ObjectMapperBuilder
     * @return 設定されたObjectMapper
     */
    @Bean
    @Primary
    public ObjectMapper objectMapper(Jackson2ObjectMapperBuilder builder) {
        return builder
                .modules(new JavaTimeModule())
                .build();
    }
}
