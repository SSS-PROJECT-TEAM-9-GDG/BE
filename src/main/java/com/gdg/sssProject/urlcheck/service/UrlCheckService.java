package com.gdg.sssProject.urlcheck.service;

import com.gdg.sssProject.urlcheck.dto.UrlCheckResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class UrlCheckService {

    @Value("${url-check.key}")
    private String apiKey;

    private final WebClient webClient;


    public UrlCheckService() {
        this.webClient = WebClient.builder()
                .baseUrl("https://www.virustotal.com/api/v3") 
                .build();
    }

    public Mono<String> submitUrlForScan(String url) {
        return webClient.post()
                .uri("/urls")
                .header("x-apikey", apiKey)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .body(BodyInserters.fromFormData("url", url)) // form-data 방식
                .retrieve()
                .bodyToMono(new ParameterizedTypeReference<Map<String, Object>>() {})  // ✅ 제네릭 타입 명확히 지정
                .map(response -> {
                    System.out.println("🔍 Response: " + response);
                    var data = (Map<String, Object>) response.get("data");
                    return (String) data.get("id");
                });
    }

    public Mono<UrlCheckResponse> getUrlScanResult(String analysisId, String url) {
        return webClient.get()
                .uri("/analyses/" + analysisId)
                .header("x-apikey", apiKey)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    System.out.println("🔍 Response: " + response);

                    var data = (Map<String, Object>) response.get("data");
                    var attributes = (Map<String, Object>) data.get("attributes");
                    if (attributes == null) {
                        System.out.println("🚨 attributes가 null입니다!");
                        return new UrlCheckResponse(url, false, 0);
                    }
                    // stats를 가져오기 전에 타입 체크
                    Object statsObj = attributes.get("stats");
                    Map<String, Object> stats = (statsObj instanceof Map) ? (Map<String, Object>) statsObj : null;

                    if (stats == null) {
                        System.out.println("🚨 stats가 null입니다! API 응답을 확인하세요.");
                        return new UrlCheckResponse(url, false, 0);
                    }

                    int maliciousCount = (int) stats.getOrDefault("malicious", 0);
                    return new UrlCheckResponse(url, maliciousCount > 0, maliciousCount);
                });
    }
}
