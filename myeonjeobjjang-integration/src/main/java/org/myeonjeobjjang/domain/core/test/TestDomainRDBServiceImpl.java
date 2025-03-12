package org.myeonjeobjjang.domain.core.test;

import lombok.RequiredArgsConstructor;
import org.myeonjeobjjang.domain.core.test.dto.TestDomainRDBRequest;
import org.myeonjeobjjang.domain.core.test.dto.TestDomainRDBResponse;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TestDomainRDBServiceImpl implements TestDomainRDBService {
    private final TestDomainRDBRepository testDomainRDBRepository;
    @Override
    public Long saveData(TestDomainRDBRequest.TestSaveDomainRDBRequest request) {
        TestEntity savedEntity = testDomainRDBRepository.save(TestEntity.builder()
                .data(request.data())
                .writer(request.writer())
            .build());
        return savedEntity.getId();
    }

    @Override
    public TestDomainRDBResponse.TestLoadDomainRDBResponse getData(Long id) {
        Optional<TestEntity> entity = testDomainRDBRepository.findById(id);
        if(entity.isEmpty()) return TestDomainRDBResponse.TestLoadDomainRDBResponse.builder()
            .data("데이터가 없습니다.")
            .writer("데이터가 없습니다.")
            .build();
        return TestDomainRDBResponse.TestLoadDomainRDBResponse.builder()
            .data(entity.get().getData())
            .writer(entity.get().getWriter())
            .build();
    }
}
