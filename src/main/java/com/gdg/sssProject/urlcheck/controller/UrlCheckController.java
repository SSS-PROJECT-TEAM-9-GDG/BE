package com.gdg.sssProject.urlcheck.controller;

import com.gdg.sssProject.urlcheck.dto.UrlCheckRequest;
import com.gdg.sssProject.urlcheck.dto.UrlCheckResponse;
import com.gdg.sssProject.urlcheck.service.UrlCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/urlcheck")
@RequiredArgsConstructor
@Tag(name = "UrlCheck", description = "URL 안전성 검사 API")
public class UrlCheckController {

    private final UrlCheckService urlCheckService;

    @PostMapping("/scan")
    @Operation(summary = "URL 스캔 API", description = "입력된 URL을 스캔하여 안전성을 검사합니다.")
    public Mono<ResponseEntity<UrlCheckResponse>> scanUrl(@RequestBody UrlCheckRequest request) {
        return urlCheckService.submitUrlForScan(request.getUrl())
                .flatMap(analysisId -> urlCheckService.getUrlScanResult(analysisId, request.getUrl()))
                .map(ResponseEntity::ok);
    }
}