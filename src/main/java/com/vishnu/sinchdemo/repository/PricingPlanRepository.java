package com.vishnu.sinchdemo.repository;

import com.vishnu.sinchdemo.domain.PricingPlan;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface PricingPlanRepository extends JpaRepository<PricingPlan, Long> {
    
    // PREVENTS N+1 PROBLEM: Fetches plans and their rules in a single SQL JOIN query.
    @EntityGraph(attributePaths = {"rules"})
    List<PricingPlan> findByCountryCode(String countryCode);
}
