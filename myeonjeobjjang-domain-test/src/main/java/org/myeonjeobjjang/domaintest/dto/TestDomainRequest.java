package org.myeonjeobjjang.domaintest.dto;

public class TestDomainRequest {
    public record TestSaveDomainRequest(
        String data,
        String writer
    ) {}
}
