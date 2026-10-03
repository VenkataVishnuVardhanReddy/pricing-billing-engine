package com.vishnu.sinchdemo.repository;

import com.vishnu.sinchdemo.domain.CustomerBalance;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerBalanceRepository extends JpaRepository<CustomerBalance, Long> {
}
