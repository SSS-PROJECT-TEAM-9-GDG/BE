package com.gdg.sssProject.nosieEx.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Mono;

import java.io.IOException;

@Service
@Slf4j
public class WatermarkService {

    @Value("${api.spam-check.key}")
    private String apiKey;  // API Key를 설정해두었을 경우

    private final WebClient webClient;

    public WatermarkService(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder.baseUrl("https://apick.app/rest").build();
    }

    public byte[] addWatermarkToImage(MultipartFile file, String code) throws IOException {
        log.info("addWatermarkToImage -{}", code);
        log.info("addWatermarkToImage -{}", file);

        // Create the form data
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("pdf", file.getResource());
        body.add("code", code);

        log.info("바디 -{}", body);

        // Send the request using WebClient
        Mono<byte[]> response = webClient.post()
                .uri("/set_watermark")  // URL 경로에서 code를 제거
                .header("CL_AUTH_KEY", apiKey)
                .bodyValue(body)  // multipart/form-data로 본문을 설정
                .retrieve()
                .bodyToMono(byte[].class);

        // Block to get the response
        byte[] responseBody = response.block();

        log.info("응답 -{}", responseBody);
        // Return the image data
        return responseBody;
    }
}
