package com.vishnu.sinchdemo.controller;

import com.vishnu.sinchdemo.domain.PricingPlan;
import com.vishnu.sinchdemo.service.PricingService;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/v1/pricing")
public class PricingController {

    private final PricingService pricingService;

    public PricingController(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    // Exposes pricing logic via REST API for external dashboard clients
    @GetMapping("/{countryCode}")
    public List<PricingPlan> getPricingByCountry(@PathVariable String countryCode) {
        return pricingService.getPricingPlans(countryCode);
    }
}
