package org.myeonjeobjjang.infra.client.mockInterview;

import org.myeonjeobjjang.infra.client.mockInterview.dto.MockInterviewClientRequest.MockInterviewChatRequest;

public interface MockInterviewClient {
    String mockInterviewChat(MockInterviewChatRequest request);
}
