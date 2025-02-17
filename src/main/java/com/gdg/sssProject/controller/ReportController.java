package com.gdg.sssProject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/report")
@Tag(name = "신고 도우미 API", description = "신고 관련 정보를 제공하는 API")
public class ReportController {

    @GetMapping("/test")
    public String getTest(){
        return "hello";
    }

    @GetMapping("/info/{type}")
    @Operation(summary = "신고 정보 조회", description = "신고 유형에 따라 적절한 대응 절차를 반환합니다.")
    public ResponseEntity<Map<String, Object>> getReportInfo(@PathVariable("type") String type) {
        Map<String, Object> response = new HashMap<>();

        switch (type.toLowerCase()) {
            case "deepfake":
                response.put("title", "딥페이크란?");

                List<String> dfDescription = new ArrayList<>();
                dfDescription.add("어떤 사람이 촬영·녹음된 얼굴, 신체, 음성을 성적 욕망");
                dfDescription.add("또는 수치심을 유발할 수 있는 형태로");
                dfDescription.add("편집·합성·가공하여 만든 정보");
                response.put("description", dfDescription);

                List<String> dfCriteriaList = new ArrayList<>();
                dfCriteriaList.add("✔ 처음 동의했더라도,");
                dfCriteriaList.add("사후에 대상자의 의사에 반하여 유포된 경우");
                dfCriteriaList.add("✔ 처음부터 대상자의 동의 없이 만들어진 경우");
                response.put("criteriaList", dfCriteriaList);

                List<String> dfContact = new ArrayList<>();
                dfContact.add("✅ 국번없이 ☎1377 (유료)");
                dfContact.add("✅ 방송민원 1번, 통신민원 2번, 디지털성범죄민원 3번");
                response.put("contact", dfContact);

                response.put("chatbot", "https://pf.kakao.com/_qxltUxb");
                break;

            case "voicephishing":
                response.put("title", "보이스피싱이란?");

                List<String> vpDescription = new ArrayList<>();
                vpDescription.add("전화(Voice)와 금융사기(Phishing)의 합성어로,");
                vpDescription.add("전화나 문자 메시지를 이용해 개인 정보를 탈취하거나");
                vpDescription.add("금전을 편취하는 사기 수법");
                response.put("description", vpDescription);

                List<String> vpCriteriaList = new ArrayList<>();
                vpCriteriaList.add("✔ 의심되는 전화는 끊고 직접 기관에 확인하기");
                vpCriteriaList.add("✔ 가족, 지인에게 돈을 보내기 전 반드시 직접 확인하기");
                vpCriteriaList.add("✔ 전화로 계좌번호나 비밀번호 요구하면 100% 사기");
                response.put("criteriaList", vpCriteriaList);

                List<String> vpContact = new ArrayList<>();
                vpContact.add("✅ 은행 고객센터 또는 금융감독원(☎1332)");
                vpContact.add("✅ 경찰 신고(☎112) 및 사이버범죄 신고");
                response.put("contact", vpContact);
                response.put("center", "https://www.boho.or.kr/kr/consult/consultForm.do?menuNo=205035");
                break;

            case "smishing":
                response.put("title", "스미싱이란?");

                List<String> ssDescription = new ArrayList<>();
                ssDescription.add("SMS(문자 메시지)와 피싱(Phishing)의 합성어로,");
                ssDescription.add("문자 메시지를 이용한 금융 사기 수법");
                response.put("description", ssDescription);

                List<String> ssCriteriaList = new ArrayList<>();
                ssCriteriaList.add("✔ 출처가 불분명한 문자 속 링크 클릭하지 않기");
                ssCriteriaList.add("✔ 의심되는 번호로 온 문자는 즉시 차단하기");
                ssCriteriaList.add("✔ 백신 프로그램 설치 및 최신 버전 유지하기");
                response.put("criteriaList", ssCriteriaList);

                List<String> ssContact = new ArrayList<>();
                ssContact.add("✅ 금융사기 피해신고 : 금융감독원(☎1332)");
                ssContact.add("✅ 소액결제 차단 : 이동통신사 고객센터(☎114)");
                response.put("contact", ssContact);
                response.put("center", "https://spam.kisa.or.kr/spam/ss/ssSpamInfo.do?mi=1025");
                break;

            case "personal_data_breach":
                response.put("title", "개인정보 유출이란?");

                List<String> pdDescription = new ArrayList<>();
                pdDescription.add("주민등록번호, 계좌번호 등 개인의 중요한 정보가");
                pdDescription.add("허가되지 않은 상태로 외부로 노출되거나 도용되는 것");
                response.put("description", pdDescription);

                List<String> pdCriteriaList = new ArrayList<>();
                pdCriteriaList.add("✔ 공식 사이트 이외에는 개인정보 입력 금지");
                pdCriteriaList.add("✔ 비밀번호는 주기적으로 변경하기");
                pdCriteriaList.add("✔ 공공장소에서는 공용 Wi-Fi 사용 주의");
                response.put("criteriaList", pdCriteriaList);

                List<String> pdContact = new ArrayList<>();
                pdContact.add("✅ 명의도용 피해 신고: 한국인터넷진흥원(KISA)(☎118)");
                pdContact.add("✅ 계좌 금융 도용 피해신고 : 금융감독원(☎1332)");
                response.put("contact", pdContact);
                response.put("center", "https://privacy.kisa.or.kr/counsel/privacy/beforeCounsel.do");
                break;
            default:
                response.put("error", "해당 신고 유형이 없습니다.");
                return ResponseEntity.badRequest().body(response);
        }

        return ResponseEntity.ok(response);
    }
}

