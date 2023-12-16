package com.eventPlanner.unit.testUtils;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class UniqueValueGenerator {
    private static final AtomicLong uniqueId = new AtomicLong(ThreadLocalRandom.current().nextLong());

    public static String uniqueString() {
        return UUID.randomUUID().toString();
    }

    public static Long uniqueLong() {
        return uniqueId.getAndIncrement();
    }
}
