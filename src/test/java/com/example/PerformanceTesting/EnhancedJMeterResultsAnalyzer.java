package com.example.PerformanceTesting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EnhancedJMeterResultsAnalyzer {

    public static void main(String[] args) {
        String csvFile = "results.jtl";
        String line;
        String csvSplitBy = ",";

        int totalRequests = 0;
        int successfulRequests = 0;
        int failedRequests = 0;
        double totalResponseTime = 0.0;
        double maxResponseTime = Double.MIN_VALUE;
        double minResponseTime = Double.MAX_VALUE;
        List<Double> responseTimes = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip header
            br.readLine();

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                totalRequests++;

                // Assuming that the response code is in the 2nd column (index 1)
                String responseCode = data[1];

                // Assuming that the response time is in the 3rd column (index 2)
                double responseTime = Double.parseDouble(data[2]);
                responseTimes.add(responseTime);

                totalResponseTime += responseTime;

                if ("200".equals(responseCode)) {
                    successfulRequests++;
                } else {
                    failedRequests++;
                }

                // Update max and min response times
                maxResponseTime = Math.max(maxResponseTime, responseTime);
                minResponseTime = Math.min(minResponseTime, responseTime);
            }

            double averageResponseTime = totalResponseTime / totalRequests;
            double successRate = ((double) successfulRequests / totalRequests) * 100;

            // Calculate percentiles
            Collections.sort(responseTimes);
            double p95 = responseTimes.get((int) (responseTimes.size() * 0.95));
            double p99 = responseTimes.get((int) (responseTimes.size() * 0.99));

            // Output results
            System.out.println("Total Requests: " + totalRequests);
            System.out.println("Successful Requests: " + successfulRequests);
            System.out.println("Failed Requests: " + failedRequests);
            System.out.println("Average Response Time (ms): " + averageResponseTime);
            System.out.println("Max Response Time (ms): " + maxResponseTime);
            System.out.println("Min Response Time (ms): " + minResponseTime);
            System.out.println("95th Percentile Response Time (ms): " + p95);
            System.out.println("99th Percentile Response Time (ms): " + p99);
            System.out.println("Success Rate (%): " + successRate);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
