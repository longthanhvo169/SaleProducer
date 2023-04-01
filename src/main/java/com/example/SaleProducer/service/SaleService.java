/*
 * Copyright © 2023 by Seven System Viet Nam, JSC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited Proprietary and confidential
 *
 * Write clean code and you can sleep well at night ¯\(ツ)/¯
 *
 * Written by long.vt@7-eleven.vn
 */

package com.example.SaleProducer.service;

import com.example.SaleProducer.dto.SaleSummaryDto;
import com.example.SaleProducer.model.Sale;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.List;
import java.util.Map;

public interface SaleService {
    List<Sale> convertRecordsToSale(File file) throws IOException, ParseException;

    Map<String, SaleSummaryDto> getStringSaleSummaryDtoMap(List<Sale> saleList);

}
