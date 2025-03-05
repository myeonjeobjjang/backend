package org.myeonjeobjjang.domain.core.test.dto;

import lombok.Builder;

public class TestDomainRDBResponse {
    @Builder
    public record TestLoadDomainRDBResponse(
        String data,
        String writer
    ) {}
}
