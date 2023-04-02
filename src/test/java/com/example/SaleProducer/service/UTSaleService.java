package com.example.SaleProducer.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.*;

import com.example.SaleProducer.util.CSVUtils;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.example.SaleProducer.dto.SaleSummaryDto;
import com.example.SaleProducer.model.Sale;
import com.example.SaleProducer.model.SaleHeader;
import com.example.SaleProducer.service.impl.SaleServiceImpl;
import org.springframework.mock.web.MockMultipartFile;

@SpringBootTest
public class UTSaleService {

    private final SaleServiceImpl saleService = new SaleServiceImpl();

    @Test
    public void testConvertRecordsToSale() throws IOException, ParseException {
        // Create a sample CSV file
        String csvContent = "SalesDate,StoreName,ProductName,SalesUnits,SalesRevenue\n" +
                "20220101|Store A|Product A|10|100.0\n" +
                "20220102|Store B|Product B|20|200.0\n" +
                "20220103|Store C|Product C|30|300.0\n";
        File csvFile = new File("test.csv");
        FileWriter writer = new FileWriter(csvFile);
        writer.write(csvContent);
        writer.close();

        // Convert the CSV file to a list of Sale objects
        List<Sale> sales = saleService.convertRecordsToSale(csvFile);

        // Check if the number of sales objects is correct
        Assertions.assertEquals(3, sales.size());

        // Check if the Sale objects are correctly parsed
        Assertions.assertEquals(DateUtils.parseDate("20220101", "YYYYMMDD"),  sales.get(0).getDate());
        Assertions.assertEquals("Store A", sales.get(0).getStoreName());
        Assertions.assertEquals("Product A", sales.get(0).getProductName());
        Assertions.assertEquals(10L, sales.get(0).getSaleUnits());
        Assertions.assertEquals(100.0, sales.get(0).getSaleRevenue());
    }

}
