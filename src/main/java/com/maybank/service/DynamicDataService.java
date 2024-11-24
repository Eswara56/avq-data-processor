package com.maybank.service;

import com.maybank.data.FieldMap;
import com.maybank.exceptions.BusinessException;
import com.maybank.repository.DynamicDataRepository;
import com.maybank.repository.SystemConfigRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store the data dynamically into the database by using {@link  com.maybank.repository.DynamicDataRepository}.
 */
@Service
public class DynamicDataService {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataService.class);

    private final DynamicDataRepository dynamicDataRepository;
    private final AuditLogService auditLogService;
    SystemConfigRepository systemConfigRepository;

    public DynamicDataService(DynamicDataRepository dynamicDataRepository, AuditLogService auditLogService, SystemConfigRepository systemConfigRepository) {
        this.auditLogService = auditLogService;
        this.dynamicDataRepository = dynamicDataRepository;
        this.systemConfigRepository = systemConfigRepository;
    }


    /**
     * This method is used to process the data and store it into the database dynamically.
     *
     * @param appCode
     * @param headerTable
     * @param headerColumnAndValues
     * @return
     */
    public void processData(String appCode, String headerTable, List<List<FieldMap>> headerColumnAndValues) {
        // Start measuring execution time
        long startTime = System.currentTimeMillis();
        //Generate Dynamic SQL Query using the headerTable and headerColumnAndValues
        //Insert the data into the database
        List<String> insertQueries = new ArrayList<>();
        StringBuilder insertQuery = null;
        for ( List<FieldMap> dbRow : headerColumnAndValues) {
            insertQuery = new StringBuilder("INSERT INTO " + headerTable + " (");
            for (FieldMap fieldMap : dbRow) {
                if (!"RECORD".equals(fieldMap.getColumn())) {
                    insertQuery.append(fieldMap.getColumn()).append(",");
                } else {
                    log.debug("Record value: " + fieldMap.getValue());
                }
            }
            prepareInsertValues(dbRow, insertQuery, insertQueries);
        }
        try {
            dynamicDataRepository.insertQueries(insertQueries);

        } catch (Exception e) {
            auditLogService.log("processData", "insertQueries", " UserID: " + appCode, "forming in", " The system " + e.getMessage());
            log.debug("Error while inserting data into the database: {}", e.getMessage());
            throw new BusinessException("Error while inserting data into the database: " + e.getMessage());
        }

        // End measuring execution time
        long endTime = System.currentTimeMillis();
        // Calculate and log the execution time
        long durationInSeconds  = (endTime - startTime)/1000; // Convert nanoseconds to seconds
        System.out.println("Execution time: " + durationInSeconds  + " seconds");
        log.debug("Execution time: " + durationInSeconds  + " seconds");
    }

    /**
     * This method is used to prepare the insert values for the query.
     * @param dbRow
     * @param insertQuery
     * @param insertQueries
     */
    private static void prepareInsertValues(List<FieldMap> dbRow, StringBuilder insertQuery, List<String> insertQueries) {
        insertQuery.deleteCharAt(insertQuery.length() - 1);
        insertQuery.append(") VALUES (");
        for (FieldMap fieldMap : dbRow) {
            if (!"RECORD".equals(fieldMap.getColumn())) {
                if (fieldMap.getValue() == null || fieldMap.getValue().length() == 0) {
                    System.out.println(fieldMap);
                    log.debug("FieldMap value is null or empty: {}", fieldMap);
                }
                insertQuery.append(fieldMap.getValue()).append(",");
            }
        }
        insertQuery.deleteCharAt(insertQuery.length() - 1);
        insertQuery.append(")");
        insertQueries.add(insertQuery.toString());
    }
}
