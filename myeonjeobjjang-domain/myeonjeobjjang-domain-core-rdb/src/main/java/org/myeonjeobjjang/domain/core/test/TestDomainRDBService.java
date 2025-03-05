package org.myeonjeobjjang.domain.core.test;

import org.myeonjeobjjang.domain.core.test.dto.TestDomainRDBRequest;
import org.myeonjeobjjang.domain.core.test.dto.TestDomainRDBResponse;

public interface TestDomainRDBService {
    Long saveData(TestDomainRDBRequest.TestSaveDomainRDBRequest request);
    TestDomainRDBResponse.TestLoadDomainRDBResponse getData(Long id);
}
