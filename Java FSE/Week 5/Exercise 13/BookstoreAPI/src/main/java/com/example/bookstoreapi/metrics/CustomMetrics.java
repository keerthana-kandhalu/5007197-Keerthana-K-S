package com.example.bookstoreapi.metrices;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Component;

@Component
public class CustomMetrics {

    public CustomMetrics(MeterRegistry meterRegistry) {
        meterRegistry.counter("custom.book_requests.count").increment();
    }
}
