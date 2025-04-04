package org.myeonjeobjjang.domain.core.vectordb;

import org.springframework.ai.transformer.splitter.TextSplitter;

import java.util.Arrays;
import java.util.List;

/**
 * 자기소개와 이력서 특성상 대부분 문장을 "니다."로 마무리하여 이를 기준으로 나누는 TextSplitter
 */
public class CustomCoverLetterResumeTextSplitter extends TextSplitter {

    @Override
    protected List<String> splitText(String text) {
        String[] split = text
            .replace("\n","")
            .replace("니다. ","니다.")
            .split("(?<=니다.)");
        return Arrays.stream(split).toList();
    }
}
