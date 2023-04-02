/*
 * Copyright © 2023 by Seven System Viet Nam, JSC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited Proprietary and confidential
 *
 * Write clean code and you can sleep well at night ¯\(ツ)/¯
 *
 * Written by long.vt@7-eleven.vn
 */

package com.example.SaleProducer.service;

import com.example.SaleProducer.BaseTest;
import com.example.SaleProducer.form.KafkaMessageForm;
import com.example.SaleProducer.service.impl.KafkaServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.JSONObject;
import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.kafka.core.KafkaTemplate;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
public class UTKafkaService extends BaseTest {

    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Mock
    private ObjectMapper objectMapper;

    @InjectMocks
    private KafkaServiceImpl kafkaServiceImpl;

    @Test
    public void testCreateMessage() throws JsonProcessingException {

        KafkaMessageForm kafkaMessageForm = KafkaMessageForm.builder()
                .topic("test-topic")
                .key("test-key")
                .uid("test-uid")
                .data("{}")
                .build();

        JSONObject data = new JSONObject();
        when(objectMapper.writeValueAsString(kafkaMessageForm)).thenReturn("{}");
        when(objectMapper.readValue(anyString(), any(Class.class))).thenReturn(data);
        kafkaServiceImpl.createMessage(kafkaMessageForm);
        verify(kafkaTemplate).send(kafkaMessageForm.getTopic(), kafkaMessageForm.getKey(), data.toString());
    }
}
