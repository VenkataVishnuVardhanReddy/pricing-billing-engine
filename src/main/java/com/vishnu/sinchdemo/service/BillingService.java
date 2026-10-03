package com.vishnu.sinchdemo.service;

import com.vishnu.sinchdemo.domain.CustomerBalance;
import com.vishnu.sinchdemo.repository.CustomerBalanceRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import java.math.BigDecimal;

@Service
public class BillingService {
    private final CustomerBalanceRepository repository;

    public BillingService(CustomerBalanceRepository repository) {
        this.repository = repository;
    }

    // Strict transaction boundaries: REQUIRES_NEW ensures this operation is completely isolated.
    // Optimistic locking handles the rest.
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void processPayment(Long accountId, BigDecimal amount) {
        CustomerBalance balance = repository.findById(accountId)
            .orElseThrow(() -> new RuntimeException("Account not found"));
        balance.deduct(amount);
        repository.save(balance);
    }
}
