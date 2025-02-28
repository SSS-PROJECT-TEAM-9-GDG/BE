package com.gdg.sssProject.phonenumbercheck.controller;

import com.gdg.sssProject.phonenumbercheck.dto.SpamNumberRequest;
import com.gdg.sssProject.phonenumbercheck.service.PhoneNumCheckService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/spam")
@Tag(name = "phoneNumberCheck", description = "전화번호 체크하는 API입니다.")
public class PhoneNumCheckController {

    private final PhoneNumCheckService phoneNumCheckService;

    @PostMapping("/check")
    @Operation(summary = "전화번호 체크 API", description = "전화번호 체크 API입니다.")
    public Mono<String> checkSpam(@RequestBody SpamNumberRequest spamNumberRequest) {
        return phoneNumCheckService.checkSpamNumber(spamNumberRequest);
    }
}
