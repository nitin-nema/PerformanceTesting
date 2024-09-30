package com.example.PerformanceTesting;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileOutputStream;
import java.io.IOException;

public class JMeterExcelReportGenerator {

    public static void main(String[] args) {
        String csvFile = "results.jtl";
        String line;
        String csvSplitBy = ",";

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Performance Results");

        // Create header row
        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Total Requests");
        headerRow.createCell(1).setCellValue("Successful Requests");
        headerRow.createCell(2).setCellValue("Failed Requests");
        headerRow.createCell(3).setCellValue("Average Response Time (ms)");
        headerRow.createCell(4).setCellValue("Max Response Time (ms)");
        headerRow.createCell(5).setCellValue("Min Response Time (ms)");

        int rowNum = 1;

        try (BufferedReader br = new BufferedReader(new FileReader(csvFile))) {
            // Skip header
            br.readLine();

            int totalRequests = 0;
            int successfulRequests = 0;
            int failedRequests = 0;
            double totalResponseTime = 0.0;
            double maxResponseTime = Double.MIN_VALUE;
            double minResponseTime = Double.MAX_VALUE;

            while ((line = br.readLine()) != null) {
                String[] data = line.split(csvSplitBy);
                totalRequests++;

                String responseCode = data[1];
                double responseTime = Double.parseDouble(data[2]);

                totalResponseTime += responseTime;

                if ("200".equals(responseCode)) {
                    successfulRequests++;
                } else {
                    failedRequests++;
                }

                maxResponseTime = Math.max(maxResponseTime, responseTime);
                minResponseTime = Math.min(minResponseTime, responseTime);
            }

            double averageResponseTime = totalResponseTime / totalRequests;

            // Write results to Excel
            Row resultRow = sheet.createRow(rowNum++);
            resultRow.createCell(0).setCellValue(totalRequests);
            resultRow.createCell(1).setCellValue(successfulRequests);
            resultRow.createCell(2).setCellValue(failedRequests);
            resultRow.createCell(3).setCellValue(averageResponseTime);
            resultRow.createCell(4).setCellValue(maxResponseTime);
            resultRow.createCell(5).setCellValue(minResponseTime);

            try (FileOutputStream outputStream = new FileOutputStream("JMeter_Performance_Report.xlsx")) {
                workbook.write(outputStream);
            }

            System.out.println("Report generated successfully!");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
