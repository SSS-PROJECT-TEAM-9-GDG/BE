package com.gdg.sssProject.noise.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class NoiseRequestDto {
    private MultipartFile file;
    private double mean = 0;
    private double stdDev = 25;
}