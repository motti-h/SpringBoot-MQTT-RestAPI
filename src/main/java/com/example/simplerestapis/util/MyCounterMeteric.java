package com.example.simplerestapis.util;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class MyCounterMeteric {


    Map<String,Counter> counterMap = new HashMap<>();
    @Autowired
    MeterRegistry registry;

    private void registerNewCounter(String counterName)
    {
        counterMap.put(counterName,registry.counter(counterName));
    }

    public void inctementCounter(String counterName)
    {
        if(counterMap.containsKey(counterName))
        {
            counterMap.get(counterName).increment();
        }
        else
        {
            registerNewCounter(counterName);
        }
    }


}
