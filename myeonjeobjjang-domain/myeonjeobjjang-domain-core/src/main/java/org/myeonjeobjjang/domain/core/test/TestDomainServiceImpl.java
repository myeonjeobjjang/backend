package org.myeonjeobjjang.domain.core.test;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.test.dto.TestDomainRDBRequest;
import org.myeonjeobjjang.domain.core.test.dto.TestDomainRDBResponse;
import org.myeonjeobjjang.domain.core.test.dto.TestDomainRequest;
import org.myeonjeobjjang.domain.core.test.dto.TestDomainResponse;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TestDomainServiceImpl implements TestDomainService {
    private final TestDomainRDBService testDomainRDBService;

    @Override
    public Long saveData(TestDomainRequest.TestSaveDomainRequest request) {
        return testDomainRDBService.saveData(TestDomainRDBRequest.TestSaveDomainRDBRequest.builder()
            .data(request.data())
            .writer(request.writer())
            .build());
    }

    @Override
    public TestDomainResponse.TestLoadDomainResponse getData(Long id) {
        TestDomainRDBResponse.TestLoadDomainRDBResponse response = testDomainRDBService.getData(id);
        return TestDomainResponse.TestLoadDomainResponse.builder()
            .data(response.data())
            .writer(response.writer())
            .build();
    }
}
