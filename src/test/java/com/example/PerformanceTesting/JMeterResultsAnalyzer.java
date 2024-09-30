package com.example.PerformanceTesting;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class JMeterResultsAnalyzer {

    public static void main(String[] args) {
        String csvFile = "results.jtl";
        String line;
        String csvSplitBy = ",";

        int totalRequests = 0;
        int successfulRequests = 0;
        int failedRequests = 0;
        double totalResponseTime = 0.0;

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

                totalResponseTime += responseTime;

                if ("200".equals(responseCode)) {
                    successfulRequests++;
                } else {
                    failedRequests++;
                }
            }

            double averageResponseTime = totalResponseTime / totalRequests;
            double successRate = ((double) successfulRequests / totalRequests) * 100;

            System.out.println("Total Requests: " + totalRequests);
            System.out.println("Successful Requests: " + successfulRequests);
            System.out.println("Failed Requests: " + failedRequests);
            System.out.println("Average Response Time (ms): " + averageResponseTime);
            System.out.println("Success Rate (%): " + successRate);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
