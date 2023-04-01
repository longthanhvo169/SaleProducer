/*
 * Copyright © 2023 by Seven System Viet Nam, JSC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited Proprietary and confidential
 *
 * Write clean code and you can sleep well at night ¯\(ツ)/¯
 *
 * Written by long.vt@7-eleven.vn
 */

package com.example.SaleProducer.service.impl;

import com.example.SaleProducer.dto.SaleSummaryDto;
import com.example.SaleProducer.model.Sale;
import com.example.SaleProducer.model.SaleHeader;
import com.example.SaleProducer.service.SaleService;
import com.example.SaleProducer.util.CSVUtils;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class SaleServiceImpl implements SaleService {

    public static final String SALE_DATE_FORMAT = "YYYYMMDD";
    @Override
    public List<Sale> convertRecordsToSale(File file) throws IOException, ParseException {
        List<Sale> sales = new ArrayList<>();
        CSVParser csvRecords = parseRecordsFromFileData(file);

        for (CSVRecord record : csvRecords) {
            Sale sale = Sale.builder()
                    .date(DateUtils.parseDate(record.get(SaleHeader.SalesDate), SALE_DATE_FORMAT))
                    .storeName(record.get(SaleHeader.StoreName))
                    .productName(record.get(SaleHeader.ProductName))
                    .saleUnits(Long.valueOf(record.get(SaleHeader.SalesUnits)))
                    .saleRevenue(Double.valueOf(record.get(SaleHeader.SalesRevenue)))
                    .build();
            sales.add(sale);
        }
        return sales;
    }

    @Override
    public Map<String, SaleSummaryDto> getStringSaleSummaryDtoMap(List<Sale> saleList) {
        Map<String, SaleSummaryDto> saleSummaryDtoMap = new HashMap<>();
        for(Sale sale : saleList){
            String summaryKey = sale.getProductName() + sale.getStoreName();
            SaleSummaryDto saleSummaryDto = saleSummaryDtoMap.get(summaryKey);
            if(saleSummaryDto == null) {
                saleSummaryDto = new SaleSummaryDto(sale.getStoreName(), sale.getProductName(), sale.getSaleUnits(), sale.getSaleRevenue());
            } else {
                saleSummaryDto.setTotalUnits(saleSummaryDto.getTotalUnits() + sale.getSaleUnits());
                saleSummaryDto.setTotalRevenue(saleSummaryDto.getTotalRevenue() + sale.getSaleRevenue());
            }
            saleSummaryDtoMap.put(summaryKey, saleSummaryDto);
        }
        return saleSummaryDtoMap;
    }

    private CSVParser parseRecordsFromFileData(File file) throws IOException {
        return CSVUtils.getCSVParser(file, SaleHeader.class);
    }
}
