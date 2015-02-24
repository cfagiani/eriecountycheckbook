package com.cataractsoftware.checkbook.scraper;

import org.cataractsoftware.datasponge.DataRecord;
import org.cataractsoftware.datasponge.extractor.PdfTextExtractor;

import java.util.ArrayList;
import java.util.Collection;
import java.util.UUID;

/**
 * builds data records for the Erie County Checkbook pdfs from
 * http://www2.erie.gov/comptroller/index.php?q=taxpayer-checkbook
 *
 * @author Christopher Fagiani
 */
public class DataExtractor extends PdfTextExtractor {
    private static final String RECORD_TYPE = "checkRecord";
    private static final String HEADER = "name of the payee";
    private static final String FOOTER = "Total";


    @Override
    protected Collection<DataRecord> processText(String url, String allText) {
        Collection<DataRecord> records = new ArrayList<DataRecord>();
        if(allText != null){
            for(String line: allText.split("\n")){
                if(!line.toLowerCase().contains(HEADER) && !line.trim().startsWith(FOOTER)){
                    String temp = line.trim();
                    if(temp.length()>0){
                        DataRecord rec = new DataRecord(UUID.randomUUID().toString(),RECORD_TYPE);
                        int dateIndex =  temp.lastIndexOf(" ");
                        if(dateIndex >5) {
                            rec.setField("date", temp.substring(dateIndex).trim());
                            temp = temp.substring(0, dateIndex - 1);
                            int amountIndex = temp.lastIndexOf(" ");
                            rec.setField("amount", temp.substring(amountIndex).trim().replaceAll(",",""));
                            rec.setField("payee", temp.substring(0, amountIndex - 1).trim().replaceAll(","," "));
                            records.add(rec);
                        }
                    }
                }
            }
        }
        return records;
    }


}
