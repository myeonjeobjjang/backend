package org.myeonjeobjjang.domaintest;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TestDomainRepository extends JpaRepository<TestEntity, Long> {
}