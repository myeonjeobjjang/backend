package org.myeonjeobjjang.domain.core.test;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDomainRDBRepository extends JpaRepository<TestEntity, Long> {
}