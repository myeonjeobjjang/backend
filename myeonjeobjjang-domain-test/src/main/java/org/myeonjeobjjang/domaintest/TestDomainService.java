package org.myeonjeobjjang.domaintest;

import org.myeonjeobjjang.domaintest.dto.TestDomainRequest;
import org.myeonjeobjjang.domaintest.dto.TestDomainResponse;

public interface TestDomainService {
    Long saveData(TestDomainRequest.TestSaveDomainRequest request);
    TestDomainResponse.TestLoadDomainResponse getData(Long id);
}
