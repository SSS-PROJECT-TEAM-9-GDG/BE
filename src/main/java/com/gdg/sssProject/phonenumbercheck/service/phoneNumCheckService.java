package com.gdg.sssProject.phonenumbercheck.service;

import com.gdg.sssProject.phonenumbercheck.dto.SpamNumberRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@Service
public class phoneNumCheckService {
    private final WebClient webClient;

    private static final String API_URL = "https://apick.app/rest/check_spam_number";
    @Value("${api.spam-check.key}")
    private String apiKey;

    public phoneNumCheckService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://apick.app/rest").build();
    }

    public Mono<String> checkSpamNumber(SpamNumberRequest spamNumberRequest) {
        return webClient.post()
                .uri("/check_spam_number")
                .header("CL_AUTH_KEY", apiKey)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("number=" + spamNumberRequest.number())
                .retrieve()
                .bodyToMono(String.class)
                .onErrorReturn("Error: API 요청 실패");
    }
}
