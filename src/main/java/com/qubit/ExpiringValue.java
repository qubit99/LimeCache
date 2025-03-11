package com.qubit;

import java.util.concurrent.TimeUnit;

public class ExpiringValue<V> {
    private static final long UNSET_DURATION = -1L;
    private final V value;
    private final ExpirationPolicy expirationPolicy;
    private final long duration;
    private final TimeUnit timeUnit;

    public ExpiringValue(V value, ExpirationPolicy expirationPolicy, long duration, TimeUnit timeUnit) {
        this.value = value;
        this.expirationPolicy = expirationPolicy;
        this.duration = duration;
        this.timeUnit = timeUnit;
    }
}
