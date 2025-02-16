package com.gdg.sssProject.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Bean
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }
    @Bean
    public WebClient virusTotalWebClient(WebClient.Builder webClientBuilder) {
        return webClientBuilder
                .baseUrl("https://www.virustotal.com/api/v3")
                .build();
    }
}