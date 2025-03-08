package com.qubit;

import com.qubit.internal.ExceptionHandler;
import com.qubit.internal.NamedThreadFactory;

import java.util.*;
import java.util.concurrent.*;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiFunction;
import java.util.function.Function;

public class LimeCache<K,V> implements ConcurrentMap<K,V> {

    static volatile ScheduledExecutorService cacheEvictionExecutor;
    static ThreadFactory THREAD_FACTORY;
    transient int size;
    private final int maxSize;
    private final ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
    private final Lock readLock = readWriteLock.readLock();
    private final Lock writeLock = readWriteLock.writeLock();
    private LinkedHashMap<K, V> entryMap;

    private LimeCache(final Builder<K, V> builder) {
        if (cacheEvictionExecutor == null) {
            synchronized (LimeCache.class) {
                if (cacheEvictionExecutor == null) {
                    cacheEvictionExecutor = Executors.newSingleThreadScheduledExecutor(
                            THREAD_FACTORY == null ? new NamedThreadFactory("LimeCache-evictor") : THREAD_FACTORY);
                }
            }
        }
        maxSize = builder.maxSize;
    }

    public static final class Builder<K, V> {
        private TimeUnit timeUnit = TimeUnit.SECONDS;
        private boolean variableExpiration;
        private long duration = 60;
        private int maxSize = Integer.MAX_VALUE;

        private Builder() {
        }

        @SuppressWarnings("unchecked")
        public <K1 extends K, V1 extends V> LimeCache<K1, V1> build() {
            return new LimeCache<>((Builder<K1, V1>) this);
        }

        public Builder<K, V> expiration(long duration, TimeUnit timeUnit) {
            ExceptionHandler.checkNullPointerException(timeUnit, timeUnit);
            this.duration = duration;
            this.timeUnit = timeUnit;
            return this;
        }

        public Builder<K, V> maxSize(int maxSize) {
            ExceptionHandler.checkInvalidArgument(maxSize > 0, "maxSize should be greater than 0");
            this.maxSize = maxSize;
            return this;
        }

    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        readLock.lock();
        try {
            return entryMap.containsKey(key);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public boolean containsValue(Object value) {
        readLock.lock();
        try {
            return entryMap.containsValue(value);
        } finally {
            readLock.unlock();
        }
    }

    @Override
    public V get(Object key) {
        readLock.lock();
        // read and return;
        V value = entryMap.get(key);
        readLock.unlock();
        return value;
    }

    @Override
    public V put(K key, V value) {
        return null;
    }

    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {

    }

    @Override
    public void clear() {

    }

    @Override
    public Set<K> keySet() {
        return Set.of();
    }

    @Override
    public Collection<V> values() {
        return List.of();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        return Set.of();
    }

    @Override
    public V putIfAbsent(K key, V value) {
        return null;
    }

    @Override
    public boolean remove(Object key, Object value) {
        return false;
    }

    @Override
    public boolean replace(K key, V oldValue, V newValue) {
        return false;
    }

    @Override
    public V replace(K key, V value) {
        return null;
    }

    @Override
    public V computeIfAbsent(K key, Function<? super K, ? extends V> mappingFunction) {
        return ConcurrentMap.super.computeIfAbsent(key, mappingFunction);
    }

    @Override
    public V computeIfPresent(K key, BiFunction<? super K, ? super V, ? extends V> remappingFunction) {
        return ConcurrentMap.super.computeIfPresent(key, remappingFunction);
    }
}
