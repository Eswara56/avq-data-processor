package com.maybank.repository;

import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * This class is used to store the detail data into Dynamic Detail Table.
 */
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
        //Execute the insert queries to store the data into the database
        //Use JPA EntityManager to execute the queries
        List<Integer> rowsAffected = new ArrayList<>();
        for (String query : insertQueries) {
            entityManager.createNativeQuery(query).executeUpdate();
            //rowsAffected.add(result);
        }
        return rowsAffected;
    }
}
