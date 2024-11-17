package com.maybank.service;

import com.maybank.data.FieldMap;
import com.maybank.repository.DynamicDataRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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
     * @param headerTable
     * @param headerColumnAndValues
     * @return
     */
    public List<Integer> processData(String headerTable, List<List<FieldMap>> headerColumnAndValues) {
        //Generate Dynamic SQL Query using the headerTable and headerColumnAndValues
        //Insert the data into the database
        List<String> insertQueries = new ArrayList<>();
        StringBuilder insertQuery = null;
        for (List<FieldMap> dbRow : headerColumnAndValues) {
            insertQuery = new StringBuilder("INSERT INTO " + headerTable + " (");
            for (FieldMap fieldMap : dbRow) {
                insertQuery.append(fieldMap.getColumn()).append(",");
            }
            insertQuery.deleteCharAt(insertQuery.length() - 1);
            insertQuery.append(") VALUES (");
            for (FieldMap fieldMap : dbRow) {
                insertQuery.append(fieldMap.getValue()).append(",");
            }
            insertQuery.deleteCharAt(insertQuery.length() - 1);
            insertQuery.append(")");
            insertQueries.add(insertQuery.toString());
        }
        return dynamicDataRepository.insertData(insertQueries);
    }
}
