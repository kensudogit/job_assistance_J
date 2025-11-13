package com.jobassistance;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

/**
 * 就労支援システムのメインアプリケーションクラス
 */
@SpringBootApplication
@EnableJpaAuditing
public class JobAssistanceApplication {

    /**
     * アプリケーションのエントリーポイント
     * 
     * @param args コマンドライン引数
     */
    public static void main(String[] args) {
        SpringApplication.run(JobAssistanceApplication.class, args);
    }
}

