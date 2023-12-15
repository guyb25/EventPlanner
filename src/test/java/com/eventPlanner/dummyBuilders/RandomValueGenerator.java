package com.eventPlanner.dummyBuilders;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.atomic.AtomicLong;

public class RandomValueGenerator {
    private static final AtomicLong uniqueId = new AtomicLong(ThreadLocalRandom.current().nextLong());

    public static String randomUniqueString() {
        return UUID.randomUUID().toString();
    }

    public static Long randomUniqueLong() {
        return uniqueId.getAndIncrement();
    }
}
