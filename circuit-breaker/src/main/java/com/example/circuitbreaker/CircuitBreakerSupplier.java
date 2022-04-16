package com.example.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import org.springframework.context.annotation.Bean;

import java.time.Duration;
import java.util.function.Supplier;


public class CircuitBreakerSupplier implements Supplier<CircuitBreaker> {

    @Bean
    @Override
    public CircuitBreaker get() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .slowCallRateThreshold(50)
                .slowCallDurationThreshold(Duration.ofSeconds(3))
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(5).build();
        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker circuitBreakerWithCustomConfig = (CircuitBreaker) circuitBreakerRegistry
                .circuitBreaker("name2", circuitBreakerConfig);
        return circuitBreakerWithCustomConfig;
    }
}
