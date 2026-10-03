package com.vishnu.sinchdemo.service;

import com.vishnu.sinchdemo.grpc.PricingRequest;
import com.vishnu.sinchdemo.grpc.PricingResponse;
import com.vishnu.sinchdemo.grpc.PricingRuleDto;
import com.vishnu.sinchdemo.grpc.PricingServiceGrpc;
import com.vishnu.sinchdemo.domain.PricingPlan;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class PricingGrpcService extends PricingServiceGrpc.PricingServiceImplBase {
    
    private final PricingService pricingService;

    public PricingGrpcService(PricingService pricingService) {
        this.pricingService = pricingService;
    }

    // High performance gRPC implementation for internal microservice communication
    @Override
    public void getPricingPlan(PricingRequest request, StreamObserver<PricingResponse> responseObserver) {
        List<PricingPlan> plans = pricingService.getPricingPlans(request.getCountryCode());
        
        if (plans.isEmpty()) {
            responseObserver.onCompleted();
            return;
        }

        PricingPlan plan = plans.get(0);
        
        List<PricingRuleDto> ruleDtos = plan.getRules().stream()
            .map(rule -> PricingRuleDto.newBuilder()
                .setId(rule.getId())
                .setOperator(rule.getOperator())
                .setPrice(rule.getPrice().toString())
                .build())
            .collect(Collectors.toList());

        PricingResponse response = PricingResponse.newBuilder()
            .setPlanId(plan.getId())
            .setCountryCode(plan.getCountryCode())
            .addAllRules(ruleDtos)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
    }
}
