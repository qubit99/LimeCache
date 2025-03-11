package com.qubit;

public abstract class LimeCacheLoader<K,V> {

    public abstract V load(K key);

}
