/*
 * Copyright © 2023 by Seven System Viet Nam, JSC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited Proprietary and confidential
 *
 * Write clean code and you can sleep well at night ¯\(ツ)/¯
 *
 * Written by long.vt@7-eleven.vn
 */

package com.example.SaleProducer.service.impl;
import com.example.SaleProducer.form.KafkaMessageForm;
import com.example.SaleProducer.service.KafkaService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaServiceImpl implements KafkaService {

    @Autowired
    private KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    private ObjectMapper objectMapper;
    @Override
    public String createMessage(KafkaMessageForm kafkaMessageForm) throws JsonProcessingException {
        JSONObject data = new JSONObject(objectMapper.writeValueAsString(kafkaMessageForm));
        kafkaTemplate.send(kafkaMessageForm.getTopic(), kafkaMessageForm.getKey(), data.toString());
        return kafkaMessageForm.getUid();
    }
}
