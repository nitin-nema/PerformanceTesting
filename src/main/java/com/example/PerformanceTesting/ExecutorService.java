package com.example.PerformanceTesting;

ExecutorService executor = Executors.newFixedThreadPool(10);
for (int i = 0; i < 10; i++) {
        executor.submit(() -> {
        // Task logic here
        });
        }
        executor.shutdown();
