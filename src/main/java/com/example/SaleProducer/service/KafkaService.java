/*
 * Copyright © 2023 by Seven System Viet Nam, JSC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited Proprietary and confidential
 *
 * Write clean code and you can sleep well at night ¯\(ツ)/¯
 *
 * Written by long.vt@7-eleven.vn
 */

package com.example.SaleProducer.service;

import com.example.SaleProducer.form.KafkaMessageForm;
import com.fasterxml.jackson.core.JsonProcessingException;

public interface KafkaService {

    public String createMessage(KafkaMessageForm kafkaMessageForm) throws JsonProcessingException;

}
