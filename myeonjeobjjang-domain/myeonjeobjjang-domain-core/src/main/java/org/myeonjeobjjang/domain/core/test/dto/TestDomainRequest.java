package org.myeonjeobjjang.domain.core.test.dto;

public class TestDomainRequest {
    public record TestSaveDomainRequest(
        String data,
        String writer
    ) {}
}
