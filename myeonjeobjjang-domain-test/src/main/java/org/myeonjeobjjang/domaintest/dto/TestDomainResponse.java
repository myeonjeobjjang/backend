package org.myeonjeobjjang.domaintest.dto;

import lombok.Builder;

public class TestDomainResponse {
    @Builder
    public record TestLoadDomainResponse(
        String data,
        String writer
    ) {}
}
