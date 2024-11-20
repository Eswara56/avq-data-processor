package com.maybank.service;

import com.maybank.data.FieldMap;
import com.maybank.repository.DynamicDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.DelegatingServerHttpResponse;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutionException;

/**
 * This class is used to store the data dynamically into the database by using {@link  com.maybank.repository.DynamicDataRepository}.
 */
@Service
@Slf4j
public class DynamicDataService {

    private final DynamicDataRepository dynamicDataRepository;

    public DynamicDataService(DynamicDataRepository dynamicDataRepository) {
        this.dynamicDataRepository = dynamicDataRepository;
    }

    /**
     * This method is used to process the data and store it into the database dynamically.
     *
     * @param headerTable
     * @param headerColumnAndValues
     * @return
     */
    public void processData(String headerTable, List<List<FieldMap>> headerColumnAndValues) {
        //Generate Dynamic SQL Query using the headerTable and headerColumnAndValues
        //Insert the data into the database
        List<String> insertQueries = new ArrayList<>();
        StringBuilder insertQuery = null;
        for (List<FieldMap> dbRow : headerColumnAndValues) {
            insertQuery = new StringBuilder("INSERT INTO " + headerTable + " (");
            for (FieldMap fieldMap : dbRow) {

                if(!"RECORD".equals(fieldMap.getColumn()))
                {
                    insertQuery.append(fieldMap.getColumn()).append(",");
                }
                else{
                    System.out.println(fieldMap.getValue());
                }
            }
            insertQuery.deleteCharAt(insertQuery.length() - 1);
            insertQuery.append(") VALUES (");
            for (FieldMap fieldMap : dbRow) {
                if(!"RECORD".equals(fieldMap.getColumn()))
                {
                    if (fieldMap.getValue() == null || fieldMap.getValue().length() == 0) {
                        System.out.println(fieldMap);
                    }
                    insertQuery.append(fieldMap.getValue()).append(",");
                }
            }
            insertQuery.deleteCharAt(insertQuery.length() - 1);
            insertQuery.append(")");
            insertQueries.add(insertQuery.toString());
        }

        try {
            dynamicDataRepository.insertQueries(insertQueries);
        } catch (Exception e) {
            System.out.println("Error while inserting data into the database: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

}
