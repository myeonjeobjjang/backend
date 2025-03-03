package org.myeonjeobjjang.appapi;

public class TestApiRequest {
    public record TestSaveApiRequest(
        String data,
        String writer
    ) {}
}
