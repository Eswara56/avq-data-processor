package com.maybank.repository;

import com.maybank.config.FileConfig;
import com.maybank.service.AuditLogService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * This class is used to store the detail data into Dynamic Detail Table.
 */
@Repository
public class DynamicDataRepository {

    private static final Logger log = LoggerFactory.getLogger(DynamicDataRepository.class);
    private final EntityManager entityManager;
    private final FileConfig fileConfig;
    private final AuditLogService auditLogService;

    public DynamicDataRepository(EntityManager entityManager, FileConfig fileConfig, AuditLogService auditLogService) {
        this.entityManager = entityManager;
        this.fileConfig = fileConfig;
        this.auditLogService = auditLogService;
    }

    /**
     * This method is used to insert the data into the database.
     * If the transaction is successful, the data will be stored in the database. Otherwise, the transaction will be rolled back.
     *
     * @param insertQueries
     */
    @Transactional
    public void insertQueries(List<String> insertQueries) {
        int batchSize = fileConfig.getBatchSize();
        //Execute the insert queries to store the data into the database
        //Use JPA EntityManager to execute the queries
        int count = 0;
        log.debug("Inserting data into database and the number of queries to be executed: {}", insertQueries.size());
        System.out.println("Inserting data into database and the number of queries to be executed: " + insertQueries.size());
        for (String query : insertQueries) {
            count++;
            try {
                entityManager.createNativeQuery(query).executeUpdate();
            } catch (Exception e) {
                log.debug("Failed Query : " + query + " " + e.getMessage());
//                auditLogService.log("insertQueries", "insertQueries", " UserID: " + appCode, "tally the credit and debit amount", " The system " +e.getMessage());
            }
            if (count > 0 && count % batchSize == 0) {
                log.debug("Flushing and clearing the entity manager after processing {} queries", batchSize);
            }
        }
        log.debug("Flushing and clearing the entity manager after processing all queries");
    }
}
