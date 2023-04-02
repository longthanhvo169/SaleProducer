/*
 * Copyright © 2023 by Seven System Viet Nam, JSC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited Proprietary and confidential
 *
 * Write clean code and you can sleep well at night ¯\(ツ)/¯
 *
 * Written by long.vt@7-eleven.vn
 */

package com.example.SaleProducer.form;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Setter
@Getter
@Builder
public class KafkaMessageForm {
    private String uid;
    private String key;
    private String topic;
    private Date sentAt;
    private Object data;
}
