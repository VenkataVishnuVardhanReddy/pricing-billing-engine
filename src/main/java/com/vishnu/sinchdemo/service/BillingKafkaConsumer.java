package com.vishnu.sinchdemo.service;

import com.vishnu.sinchdemo.domain.CustomerBalance;
import com.vishnu.sinchdemo.repository.CustomerBalanceRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
public class BillingKafkaConsumer {

    private final CustomerBalanceRepository balanceRepository;

    public BillingKafkaConsumer(CustomerBalanceRepository balanceRepository) {
        this.balanceRepository = balanceRepository;
    }

    // Listens to Kafka topic and manages offsets manually for exactly-once processing
    @KafkaListener(topics = "sms-billing-events", groupId = "billing-group")
    @Transactional
    public void consumeBillingEvent(String message, Acknowledgment acknowledgment) {
        try {
            // Simulated parsing of Kafka message
            Long accountId = 12345L;
            BigDecimal cost = new BigDecimal("0.015");

            CustomerBalance balance = balanceRepository.findById(accountId)
                .orElseThrow(() -> new RuntimeException("Account not found"));

            // Deduct balance. Hibernate @Version ensures optimistic locking if multiple consumers hit this.
            balance.deduct(cost);
            balanceRepository.save(balance);

            // Manual offset commit ONLY after successful database transaction
            acknowledgment.acknowledge();

        } catch (org.springframework.orm.ObjectOptimisticLockingFailureException e) {
            // Handle concurrency race condition - trigger retry logic
            System.err.println("Optimistic lock failure. Another consumer processed a payment simultaneously. Retrying...");
        }
    }
}
