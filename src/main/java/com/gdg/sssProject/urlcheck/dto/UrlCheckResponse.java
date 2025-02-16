package com.gdg.sssProject.urlcheck.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UrlCheckResponse {
    private String url;
    private boolean isMalicious;
    private int maliciousCount;
}