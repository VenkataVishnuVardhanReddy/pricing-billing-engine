package com.vishnu.sinchdemo.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Service;
import java.math.BigDecimal;

@Service
public class BillingKafkaConsumer {

    private final BillingService billingService;

    public BillingKafkaConsumer(BillingService billingService) {
        this.billingService = billingService;
    }

    // Message Driven Architecture: Listens to Kafka and utilizes manual consumer offset management
    @KafkaListener(topics = "sms-billing-events", groupId = "billing-group")
    public void consumeBillingEvent(String message, Acknowledgment acknowledgment) {
        try {
            Long accountId = 12345L; // Simulated parsing
            BigDecimal cost = new BigDecimal("0.015");

            // Process payment in a strictly bounded transaction
            billingService.processPayment(accountId, cost);

            // Commit offset ONLY after transaction commits safely
            acknowledgment.acknowledge();

        } catch (org.springframework.orm.ObjectOptimisticLockingFailureException e) {
            System.err.println("Optimistic lock failure. Safe retry triggered.");
        }
    }
}
