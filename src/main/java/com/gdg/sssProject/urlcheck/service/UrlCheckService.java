package com.gdg.sssProject.urlcheck.service;

import com.gdg.sssProject.urlcheck.dto.UrlCheckResponse;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class UrlCheckService {

    @Autowired
    private Environment environment; // Spring 환경 변수 접근

    @Value("${api.url-check.key:@null}")
    private String apiKey;

    private final WebClient webClient;

    public UrlCheckService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://www.virustotal.com/api/v3/urls").build();
    }

    @PostConstruct
    public void init() {
        // 1. `@Value`로 가져온 값 확인
        System.out.println("🛠️ API Key from @Value: " + apiKey);

        // 2. `Environment`를 통해 직접 가져온 값 확인
        String envApiKey = environment.getProperty("api.url-check.key");
        System.out.println("🌍 API Key from Environment: " + envApiKey);

        // 3. 환경 변수에서 직접 가져오기 (디버깅)
        System.out.println("🔍 VT_API_KEY from System.getenv(): " + System.getenv("VT_API_KEY"));
    }

    public Mono<String> submitUrlForScan(String url) {
        return webClient.post()
                .header("x-apikey", apiKey)
                .bodyValue(Map.of("url", url))
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> (String) ((Map<String, Object>) response.get("data")).get("id"));
    }

    public Mono<UrlCheckResponse> getUrlScanResult(String analysisId, String url) {
        return webClient.get()
                .uri("https://www.virustotal.com/api/v3/urls/" + analysisId)
                .header("x-apikey", apiKey)
                .retrieve()
                .bodyToMono(Map.class)
                .map(response -> {
                    Map<String, Object> data = (Map<String, Object>) response.get("data");
                    Map<String, Object> attributes = (Map<String, Object>) data.get("attributes");
                    Map<String, Object> lastAnalysisStats = (Map<String, Object>) attributes.get("last_analysis_stats");

                    int maliciousCount = (int) lastAnalysisStats.get("malicious");
                    return new UrlCheckResponse(url, maliciousCount > 0, maliciousCount);
                });
    }
}