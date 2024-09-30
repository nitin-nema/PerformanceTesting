package com.example.PerformanceTesting;

CompletableFuture.supplyAsync(() -> fetchData())
        .thenApply(data -> process(data))
        .thenAccept(result -> store(result));

//caching
Cache<String, Object> cache = CacheManager.getInstance().getCache("myCache");
cache.put("key", value);
Object value = cache.get("key");
