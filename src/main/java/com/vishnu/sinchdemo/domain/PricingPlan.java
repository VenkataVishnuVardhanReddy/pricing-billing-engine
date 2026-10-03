package com.vishnu.sinchdemo.domain;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "pricing_plans")
public class PricingPlan {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String countryCode;

    // LAZY fetching is default for OneToMany. We solve N+1 in the Repository.
    @OneToMany(mappedBy = "pricingPlan", fetch = FetchType.LAZY)
    private List<PricingRule> rules;

    public Long getId() { return id; }
    public String getCountryCode() { return countryCode; }
    public List<PricingRule> getRules() { return rules; }
}
