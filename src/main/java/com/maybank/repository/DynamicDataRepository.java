package com.maybank.repository;

import com.maybank.config.FileConfig;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * This class is used to store the detail data into Dynamic Detail Table.
 */
@Slf4j
@Repository
public class DynamicDataRepository {

    private final EntityManager entityManager;
    private final FileConfig fileConfig;

    public DynamicDataRepository(EntityManager entityManager, FileConfig fileConfig) {
        this.entityManager = entityManager;
        this.fileConfig = fileConfig;
    }
//donr sir
    @Transactional
    public void insertQueriesNew(List<String> insertQueries) throws Exception {
        int batchSize = fileConfig.getBatchSize();

        // Partition the queries for thread-level batching
        List<List<String>> partitions = new ArrayList<>();
        for (int i = 0; i < insertQueries.size(); i += batchSize) {
            partitions.add(insertQueries.subList(i, Math.min(i + batchSize, insertQueries.size())));
        }

        System.out.println("Number of partitions: " + partitions.size());

        // Create a ThreadPoolExecutor with a fixed number of threads
        ExecutorService executorService = Executors.newFixedThreadPool(fileConfig.getThreadCount());
        List<Future<?>> futures = new ArrayList<>();

        // Submit tasks for each partition
        for (List<String> partition : partitions) {
            Future<?> future = executorService.submit(() -> {
                //insertData(partition, batchSize);
            });
            futures.add(future);
        }

        // Wait for all threads to complete
        for (Future<?> future : futures) {
            future.get(); // Blocks until the thread completes
        }

        // Shutdown the thread pool
        executorService.shutdown();

        System.out.println("All partitions processed successfully.");
    }

    /**
     * This method is used to insert the data into the database.
     * If the transaction is successful, the data will be stored in the database. Otherwise, the transaction will be rolled back.
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
            count ++;
            try {
                entityManager.createNativeQuery(query).executeUpdate();
            }catch (Exception e) {
                System.err.println("Failed Query : "+query);
            }
            //rowsAffected.add(result);
            if(count > 0 && count % batchSize == 0){
                log.debug("Flushing and clearing the entity manager after processing {} queries", batchSize);
                System.out.println("Flushing and clearing the entity manager after processing " + batchSize + " queries");
            }
        }
        System.out.println("Flushing and clearing the entity manager after processing all queries");
        log.debug("Flushing and clearing the entity manager after processing all queries");
        //Finally flush and clear the entity manager
    }

    /*@Async
    @Transactional
    public void insertData(List<String> insertQueries) throws InterruptedException, ExecutionException {
        int batchSize = fileConfig.getBatchSize();
        // Split the queries into chunks for each thread
        List<List<String>> partitions = new ArrayList<>();
        for (int i = 0; i < insertQueries.size(); i += batchSize) {
            partitions.add(insertQueries.subList(i, Math.min(i + batchSize, insertQueries.size())));
        }

        log.debug("Total number of partitions: {}", partitions.size());
        System.out.println("Total number of partitions: " + partitions.size());

        // Create a thread pool
        ExecutorService executorService = Executors.newFixedThreadPool(fileConfig.getThreadCount());

        // Process partitions asynchronously
        List<CompletableFuture<Void>> futures = partitions.stream()
                .map(partition -> CompletableFuture.runAsync(() -> {
                    log.debug("Processing partition with size: {}", partition.size());
                    System.out.println("Processing partition with size: " + partition.size());
                    int count = 0;
                    for (String query : partition) {
                        count++;
                        try {
                            entityManager.createNativeQuery(query).executeUpdate();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        if (count > 0 && count % batchSize == 0) {
                            log.debug("Flushing and clearing the entity manager after processing {} queries in partition", batchSize);
                            System.out.println("Flushing and clearing the entity manager after processing " + batchSize + " queries in partition");
                            entityManager.flush();
                            entityManager.clear();
                        }
                    }
                }, executorService))
                .collect(Collectors.toList());
        // Wait for all tasks to complete
        CompletableFuture.allOf(futures.toArray(new CompletableFuture[0])).join();

        // Shutdown the executor service
        executorService.shutdown();

        // Flush and clear the entity manager after all threads complete
        entityManager.flush();
        entityManager.clear();

        System.out.println("Flushing and clearing the entity manager after processing all queries");
        log.debug("Flushing and clearing the entity manager after processing all queries");
    }*/
}
