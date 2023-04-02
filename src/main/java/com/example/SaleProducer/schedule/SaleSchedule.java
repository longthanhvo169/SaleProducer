/*
 * Copyright © 2023 by Seven System Viet Nam, JSC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited Proprietary and confidential
 *
 * Write clean code and you can sleep well at night ¯\(ツ)/¯
 *
 * Written by long.vt@7-eleven.vn
 */

package com.example.SaleProducer.schedule;

import com.example.SaleProducer.config.KafkaConfig;
import com.example.SaleProducer.dto.SaleSummaryDto;
import com.example.SaleProducer.form.KafkaMessageForm;
import com.example.SaleProducer.model.Sale;
import com.example.SaleProducer.service.KafkaService;
import com.example.SaleProducer.service.SaleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@Slf4j
public class SaleSchedule {

    @Autowired
    KafkaService kafkaService;

    @Autowired
    SaleService saleService;

    @Autowired
    KafkaConfig kafkaConfig;

    private int countToRotateReadFile;

    private String fileNames[] = new String[] {
            "/data/Sales_20221001_20221031.psv",
            "/data/Sales_20221101_20221130.psv",
            "/data/Sales_20221201_20221231.psv" };

    @Scheduled(fixedRate = 5000)
    public void sendSales() {

        String uid = UUID.randomUUID().toString();
        try {

            countToRotateReadFile = countToRotateReadFile % fileNames.length;
            String file = getClass().getResource(fileNames[countToRotateReadFile]).getFile();
            countToRotateReadFile++;

            List<SaleSummaryDto> saleSummaryList = getSaleSummaryDtos(file);
            if (CollectionUtils.isEmpty(saleSummaryList)) {
                log.info("List empty...");
                return;
            }

            KafkaMessageForm form = KafkaMessageForm.builder()
                    .uid(uid)
                    .key(kafkaConfig.saleTopic().name())
                    .topic(kafkaConfig.saleTopic().name())
                    .sentAt(new Date())
                    .data(saleSummaryList)
                    .build();

            kafkaService.createMessage(form);
        } catch (Exception ex) {
            log.error("Producing Failed - UID: {} - Error: {}", uid, ex);
        }

    }

    private List<SaleSummaryDto> getSaleSummaryDtos(String file) throws IOException, ParseException {
        List<Sale> saleList = saleService.convertRecordsToSale(new File(file));
        if(CollectionUtils.isEmpty(saleList)) {
            return null;
        }
        Map<String, SaleSummaryDto> saleSummaryDtoMap = saleService.getStringSaleSummaryDtoMap(saleList);

        List<SaleSummaryDto> saleSummaryList = saleSummaryDtoMap.values().stream().collect(Collectors.toList());
        Collections.sort(saleSummaryList, Comparator.comparing(SaleSummaryDto::getStoreName));
        return saleSummaryList;
    }
}
