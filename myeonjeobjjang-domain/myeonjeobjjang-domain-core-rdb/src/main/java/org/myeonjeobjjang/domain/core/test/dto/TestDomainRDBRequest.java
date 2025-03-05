package org.myeonjeobjjang.domain.core.test.dto;

import lombok.Builder;

public class TestDomainRDBRequest {
    @Builder
    public record TestSaveDomainRDBRequest(
        String data,
        String writer
    ) {}
}
