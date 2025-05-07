package com.genai.stockprediction.service;

import com.genai.stockprediction.config.AlphaVantageClient;
import com.genai.stockprediction.config.GeminiClient;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class StockService {
    public void predictStock(String stockSymbol, HttpServletResponse response) throws Exception {
        Workbook workbook = new XSSFWorkbook();
        try {
            Sheet sheet = workbook.createSheet("Stock Data");
            Map<String, Double> stockData = AlphaVantageClient.fetchDailyClosePrices(stockSymbol);

            Row header = sheet.createRow(0);
            header.createCell(0).setCellValue("Date");
            header.createCell(1).setCellValue("Adjusted Close");
            StringBuilder promptBuilder = new StringBuilder();
            promptBuilder.append("Analyze the following historical stock prices for ").append(stockSymbol).append(" for trend insights and predict the stock price for near future:\n");
            promptBuilder.append("Date,Price\n");
            int rowNum = 1;
            for (Map.Entry<String, Double> entry : stockData.entrySet()) {
                Row row = sheet.createRow(rowNum++);
                row.createCell(0).setCellValue(entry.getKey());
                row.createCell(1).setCellValue(entry.getValue());
                promptBuilder.append(entry.getKey()).append(",").append(entry.getValue()).append("\n");
            }

            String prediction = GeminiClient.callGeminiApi(promptBuilder.toString());
            Sheet aiSheet = workbook.createSheet("AI Prediction");
            aiSheet.createRow(0).createCell(0).setCellValue("Gemini Insight");
            aiSheet.createRow(1).createCell(0).setCellValue(prediction);

            response.setHeader("Content-Disposition", "attachment; fileName=" + stockSymbol + "_forecast.xlsx");
            workbook.write(response.getOutputStream());
        } catch (Exception ex) {
            throw new Exception("Error occurred while generating the excel", ex);
        } finally {
            workbook.close();
        }
    }
}
