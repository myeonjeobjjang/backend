package org.myeonjeobjjang.domaintest;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domaintest.dto.TestDomainRequest;
import org.myeonjeobjjang.domaintest.dto.TestDomainResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestDomainServiceImpl implements TestDomainService {
    private final TestDomainRepository testDomainRepository;
    @Override
    public Long saveData(TestDomainRequest.TestSaveDomainRequest request) {
        TestEntity savedEntity = testDomainRepository.save(TestEntity.builder()
                .data(request.data())
                .writer(request.writer())
            .build());
        return savedEntity.getId();
    }

    @Override
    public TestDomainResponse.TestLoadDomainResponse getData(Long id) {
        Optional<TestEntity> entity = testDomainRepository.findById(id);
        if(entity.isEmpty()) return TestDomainResponse.TestLoadDomainResponse.builder()
            .data("데이터가 없습니다.")
            .writer("데이터가 없습니다.")
            .build();
        return TestDomainResponse.TestLoadDomainResponse.builder()
            .data(entity.get().getData())
            .writer(entity.get().getWriter())
            .build();
    }
}
