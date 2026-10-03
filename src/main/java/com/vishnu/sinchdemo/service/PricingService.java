package com.vishnu.sinchdemo.service;

import com.vishnu.sinchdemo.domain.PricingPlan;
import com.vishnu.sinchdemo.repository.PricingPlanRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
public class PricingService {
    private final PricingPlanRepository repository;

    public PricingService(PricingPlanRepository repository) {
        this.repository = repository;
    }

    // Clean service layer design. Uses @EntityGraph to prevent N+1 persistence pitfalls.
    @Transactional(readOnly = true)
    public List<PricingPlan> getPricingPlans(String countryCode) {
        return repository.findByCountryCode(countryCode);
    }
}
