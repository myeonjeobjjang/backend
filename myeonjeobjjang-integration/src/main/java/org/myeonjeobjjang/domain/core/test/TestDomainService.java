package org.myeonjeobjjang.domain.core.test;

import org.myeonjeobjjang.domain.core.test.dto.TestDomainRequest;
import org.myeonjeobjjang.domain.core.test.dto.TestDomainResponse;
import org.springframework.stereotype.Service;

@Service
public interface TestDomainService {
    Long saveData(TestDomainRequest.TestSaveDomainRequest request);
    TestDomainResponse.TestLoadDomainResponse getData(Long id);
}
