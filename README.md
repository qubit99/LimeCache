# LimeCache

An attempt of concurrent expiring cache in Java.


Phase 1 feature:

-> Internally use LinkedHashMap to store expiring entries
-> Provide an interface to client for get, containsKey, containsValues, reload, load in cacheLoader, refreshAll, threadFactory for async reloading of expired keys
