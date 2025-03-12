package org.myeonjeobjjang.domain.core.test.dto;

import lombok.Builder;

public class TestDomainResponse {
    @Builder
    public record TestLoadDomainResponse(
        String data,
        String writer
    ) {}
}
