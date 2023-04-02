/*
 * Copyright © 2023 by Seven System Viet Nam, JSC - All Rights Reserved
 * Unauthorized copying of this file, via any medium is strictly prohibited Proprietary and confidential
 *
 * Write clean code and you can sleep well at night ¯\(ツ)/¯
 *
 * Written by long.vt@7-eleven.vn
 */

package com.example.SaleProducer.util;

import com.example.SaleProducer.exception.CsvParsingException;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;

import java.io.*;
@Slf4j
public class CSVUtils {

    private static final char CSV_DELIMITER = '|';

    public static CSVParser getCSVParser(File file, Class<? extends Enum<?>> headers) {

        log.info("Parsing CSV file {} ...", file.getName());
        try {
            InputStream is = CSVUtils.class.getClassLoader().getResourceAsStream(file.getName());
            final Reader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));
            final CSVFormat csvFormat = CSVFormat.EXCEL
                    .withHeader(headers)
                    .withDelimiter(CSV_DELIMITER)
                    .withIgnoreSurroundingSpaces()
                    .withIgnoreEmptyLines()
                    .withSkipHeaderRecord();

            return csvFormat.parse(reader);
        } catch (IOException e) {
            log.error("Error occurred while parsing CSV file ", e);
            throw new CsvParsingException("Error occurred while parsing CSV file ", e);
        }
    }

}
