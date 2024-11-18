package com.maybank.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store the detail data into Dynamic Detail Table.
 */
@Slf4j
@Repository
public class DynamicDataRepository {

    private final EntityManager entityManager;

    public DynamicDataRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    /**
     * This method is used to insert the data into the database.
     * If the transaction is successful, the data will be stored in the database. Otherwise, the transaction will be rolled back.
     * @param insertQueries
     */
    @Transactional
    public List<Integer> insertData(List<String> insertQueries) {
        int batchSize = 30;
        //Execute the insert queries to store the data into the database
        //Use JPA EntityManager to execute the queries
        int count = 0;
        List<Integer> rowsAffected = new ArrayList<>();
        log.debug("Inserting data into database and the number of queries to be executed: {}", insertQueries.size());
        System.out.println("Inserting data into database and the number of queries to be executed: " + insertQueries.size());
        for (String query : insertQueries) {
            count ++;
            entityManager.createNativeQuery(query).executeUpdate();
            //rowsAffected.add(result);
            if(count > 0 && count % batchSize == 0){
                log.debug("Flushing and clearing the entity manager after processing {} queries", batchSize);
                System.out.println("Flushing and clearing the entity manager after processing " + batchSize + " queries");
                entityManager.flush();
                entityManager.clear();
            }
        }
        System.out.println("Flushing and clearing the entity manager after processing all queries");
        log.debug("Flushing and clearing the entity manager after processing all queries");
        //Finally flush and clear the entity manager
        entityManager.flush();
        entityManager.clear();
        return rowsAffected;
    }
}
