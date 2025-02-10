package com.gdg.sssProject.phonenumbercheck.controller;

import com.gdg.sssProject.phonenumbercheck.dto.SpamNumberRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.gdg.sssProject.phonenumbercheck.service.phoneNumCheckService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spam")
@Tag(name = "phoneNumberCheck", description = "전화번호 체크하는 API입니다.")
public class phoneNumCheckController {

    private final phoneNumCheckService phoneNumCheckService;

    @PostMapping("/check")
    @Operation(summary = "전화번호 체크 API", description = "전화번호 체크 API입니다.")
    public String checkSpam(@RequestBody SpamNumberRequest spamNumberRequest) {
        return phoneNumCheckService.checkSpamNumber(spamNumberRequest);
    }
}
