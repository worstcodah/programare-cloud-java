package com.example.circuitbreaker;

import io.github.resilience4j.circuitbreaker.CircuitBreaker;
import io.github.resilience4j.circuitbreaker.CircuitBreakerConfig;
import io.github.resilience4j.circuitbreaker.CircuitBreakerRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.time.Duration;


@RestController
public class CircuitBreakerController {

    private static final Logger LOGGER = LoggerFactory.getLogger(CircuitBreakerController.class);

    @Autowired
    private CircuitBreaker countCircuitBreaker;

    @Autowired
    private HttpBinService httpBinService;


    @Bean
    private CircuitBreaker getCircuitBreaker() {
        CircuitBreakerConfig circuitBreakerConfig = CircuitBreakerConfig.custom()
                .slowCallRateThreshold(50)
                .slowCallDurationThreshold(Duration.ofSeconds(3))
                .slidingWindowType(CircuitBreakerConfig.SlidingWindowType.COUNT_BASED)
                .slidingWindowSize(5).build();
        CircuitBreakerRegistry circuitBreakerRegistry =
                CircuitBreakerRegistry.of(circuitBreakerConfig);
        CircuitBreaker circuitBreakerWithCustomConfig = circuitBreakerRegistry
                .circuitBreaker("name", circuitBreakerConfig);
        return circuitBreakerWithCustomConfig;
    }



    /*
      This method should receive as body an integer which is the numberOfRuns. You need to decorate the circuit breaker using
      a supplier and call the endpoint without delay from HttpBinService.
      Loop through number of runs and fetch the response based on the supplier. If there is an exception, throw 500 error.
      This simulates a number of calls to an endpoint without a delay. Log some messages
    */
    @GetMapping("/no-delay/without-fallback")
    public ResponseEntity<?> noDelayedCall(@RequestBody int numberOfRuns) {

        //CircuitBreaker.decorateSupplier(countCircuitBreaker, new CircuitBreakerSupplier());
        try {
            for (int i = 0; i < numberOfRuns; ++i) {
                countCircuitBreaker.executeSupplier(() -> httpBinService.getWithoutDelay());
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok("It's ok");
    }

    /*
      This method should receive as body an integer which is the numberOfRuns and a path variable which is the delay in seconds
      for the request. You need to decorate the circuit breaker using a supplier and call the endpoint with the delay from HttpBinService.
      Loop through number of runs and fetch the response based on the supplier. If there is an exception, throw 500 error.
      This simulates a number of calls to an endpoint that has a specific delay. Log some messages
    */
    @GetMapping("/delay/with-fallback/{seconds}")
    @io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker(name = "method2")
    public ResponseEntity<?> delayedCall(@RequestBody int numberOfRuns, @PathVariable int seconds) {
        //CircuitBreaker.decorateSupplier(countCircuitBreaker, new CircuitBreakerSupplier());
        try {
            for (int i = 0; i < numberOfRuns; ++i) {
                countCircuitBreaker.executeSupplier(() -> httpBinService.getWithDelay(seconds));
            }
        } catch (Exception exception) {
            return new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return ResponseEntity.ok("It's ok");
    }
}
