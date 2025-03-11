package com.qubit;

import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicReference;

public class ExpiringEntry<K,V> implements Comparable<ExpiringEntry<K, V>>{

    final AtomicLong expirationNanos;
    final AtomicLong expectedExpiration;
    final AtomicReference<ExpirationPolicy> expirationPolicy;
    final K key;
    /** Guarded by "this" */
    volatile Future<?> entryFuture;
    /** Guarded by "this" */
    V value;
    /** Guarded by "this" */
    volatile boolean scheduled;

    public ExpiringEntry(AtomicLong expirationNanos, AtomicReference<ExpirationPolicy> expirationPolicy, K key) {
        this.expirationNanos = expirationNanos;
        this.expectedExpiration = new AtomicLong();
        this.expirationPolicy = expirationPolicy;
        this.key = key;
        resetExpiration();
    }

    @Override
    public int compareTo(ExpiringEntry<K, V> o) {
        return 0;
    }

    synchronized V getValue() {
        return value;
    }

    void resetExpiration() {
        expectedExpiration.set(expirationNanos.get() + System.nanoTime());
    }
}
