package com.vishnu.sinchdemo.domain;

import jakarta.persistence.*;
import java.math.BigDecimal;

@Entity
@Table(name = "pricing_rules")
public class PricingRule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String operator;
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "plan_id")
    private PricingPlan pricingPlan;

    public Long getId() { return id; }
    public String getOperator() { return operator; }
    public BigDecimal getPrice() { return price; }
}
