package com.gdg.sssProject.nosieEx.dto;

import lombok.Getter;
import org.springframework.web.multipart.MultipartFile;

@Getter
public class WatermarkRequest {
    private MultipartFile pdf;
    private String code;
}
