package org.myeonjeobjjang.bootapi;

public class TestApiRequest {
    public record TestSaveApiRequest(
        String data,
        String writer
    ) {}
}
