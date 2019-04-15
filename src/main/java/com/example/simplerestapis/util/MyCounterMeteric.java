package com.example.simplerestapis.util;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.stereotype.Service;

@Service
public class MyCounterMeteric {

    private final Counter restCallsCounter;
    private final Counter mqttMessageCounter;
    public MyCounterMeteric(MeterRegistry registry) {
        this.restCallsCounter = registry.counter("services.rest.counter");
        this.mqttMessageCounter = registry.counter("services.mqtt.counter");
    }


    public void countMqttMessage() {
        mqttMessageCounter.increment();
    }
    public void countRestCall()
    {
        restCallsCounter.increment();
    }
}
