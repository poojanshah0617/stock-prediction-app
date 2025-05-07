package com.genai.stockprediction.controller;

import com.genai.stockprediction.service.StockService;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/stock")
public class StockController {
    @Autowired
    private StockService stockService;

    @GetMapping(value = "/predict",produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public void predictStock(@RequestParam("symbol") String stockSymbol, HttpServletResponse response) throws Exception {
        stockService.predictStock(stockSymbol, response);
    }
}
