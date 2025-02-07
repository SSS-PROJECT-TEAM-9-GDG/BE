package com.gdg.sssProject.phonenumbercheck;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Tag(name="phoneNumberCheck", description = "전화번호 체크하는 API입니다.")

public class phoneNumCheckController {


}
