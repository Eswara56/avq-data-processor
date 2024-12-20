package com.maybank.config;


import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;


@Configuration
@ConfigurationProperties(prefix = "queries")
public class QueryConfig {

    private Map<String, String> queries;

    /**
     * Method to fetch the query from queries map
     */
    public String getQuery(String queryName) {
        return queries.get(queryName);
    }
}
