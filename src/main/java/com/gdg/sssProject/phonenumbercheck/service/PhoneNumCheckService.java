package com.gdg.sssProject.phonenumbercheck.service;

import com.gdg.sssProject.phonenumbercheck.dto.SpamNumberRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Service
public class PhoneNumCheckService {

    private final WebClient webClient;

    @Value("${api.spam-check.key}")
    private String apiKey;

    public PhoneNumCheckService(WebClient webClient) {
        this.webClient = webClient;
    }

    @Cacheable(value = "spamNumberCache", key = "#spamNumberRequest.number()")
    public Mono<String> checkSpamNumber(SpamNumberRequest spamNumberRequest) {
        String phoneNumber = spamNumberRequest.number();
        String numericPhoneNumber = phoneNumber.replaceAll("[^0-9]", "");


        if (!phoneNumber.matches("^[0-9\\-]+$")) {
            return Mono.error(new IllegalArgumentException("유효하지 않은 전화번호 형식입니다 (숫자만 허용됨): " + phoneNumber));
        }


        if (numericPhoneNumber.length() > 11) {
            return Mono.error(new IllegalArgumentException("유효하지 않은 전화번호 형식입니다 (11자 초과): " + phoneNumber));
        }

        return webClient.post()
                .uri("https://apick.app/rest/check_spam_number")
                .header("CL_AUTH_KEY", apiKey)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .bodyValue("number=" + phoneNumber)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(e -> Mono.just("Error: " + e.getMessage()));
    }
}
