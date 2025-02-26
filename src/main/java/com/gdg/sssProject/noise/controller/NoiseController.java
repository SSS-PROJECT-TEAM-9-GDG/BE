package com.gdg.sssProject.noise.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import com.gdg.sssProject.noise.service.NoiseService;

@RestController
@RequestMapping("/api/noise")
@Tag(name = "Noise Processing", description = "이미지에 노이즈를 추가하는 API입니다.")
public class NoiseController {

    private final NoiseService noiseService;

    public NoiseController(NoiseService noiseService) {
        this.noiseService = noiseService;
    }

    @PostMapping(value = "/apply", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)    @Operation(summary = "이미지 노이즈 추가 및 다운로드", description = "사용자가 업로드한 이미지에 노이즈를 추가한 후 즉시 다운로드할 수 있는 API입니다.")
    public ResponseEntity<byte[]> applyNoise(
            @RequestParam("file") MultipartFile file,
            @RequestParam("level") String level) {

        try {
            byte[] noisyImage = noiseService.applyGaussianNoise(file, level);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            headers.setContentDispositionFormData("attachment", "noisy-image.jpg"); // 파일명 설정

            return ResponseEntity.ok()
                    .headers(headers)
                    .body(noisyImage);

        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
