package org.myeonjeobjjang.techRecruit;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.infra.ai.AiService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class TechRecruitController {
    private final AiService aiService;

    @GetMapping
    public String simpleQuestion(@RequestParam String question) {
        return aiService.requestSimpleQuestion(question);
    }
}
