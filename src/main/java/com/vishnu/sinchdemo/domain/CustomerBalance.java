package com.vishnu.sinchdemo.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "customer_balances")
public class CustomerBalance {
    @Id
    private Long accountId;
    
    private BigDecimal balance;

    // OPTIMISTIC LOCKING: Prevents concurrent race conditions when consuming Kafka messages
    @Version
    private Long version;

    public void deduct(BigDecimal amount) {
        this.balance = this.balance.subtract(amount);
    }

    public Long getAccountId() { return accountId; }
    public BigDecimal getBalance() { return balance; }
    public Long getVersion() { return version; }
}
