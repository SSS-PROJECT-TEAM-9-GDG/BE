package com.gdg.sssProject.nosieEx.controller;

import com.gdg.sssProject.nosieEx.service.WatermarkService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api")
@Tag(name = "watermark Processing", description = "이미지에 워터마크 추가하는 API입니다.")
@RequiredArgsConstructor
public class WatermarkController {

    private final WatermarkService watermarkService;

    @PostMapping(value = "/add-watermark", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "이미지 노이즈 추가 및 다운로드", description = "사용자가 업로드한 이미지에 노이즈를 추가한 후 즉시 다운로드할 수 있는 API입니다.")
    public ResponseEntity<byte[]> addWatermark(
            @RequestPart("pdf") MultipartFile file,
            @RequestPart("code") String code) throws IOException {

        // 서비스 호출하여 외부 API 요청 후 이미지 받기
        byte[] image = watermarkService.addWatermarkToImage(file, code);

        // 결과를 프론트에 반환 (이미지 데이터)
        return ResponseEntity
                .status(HttpStatus.OK)
                .contentType(MediaType.IMAGE_PNG)
                .body(image);
    }
}
