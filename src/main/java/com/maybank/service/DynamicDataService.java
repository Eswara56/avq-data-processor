package com.maybank.service;

import com.maybank.data.FieldMap;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * This class is used to store the data dynamically into the database by using {@link  com.maybank.repository.DynamicDataRepository}.
 */
@Service
public class DynamicDataService {

    public void processHeaderData(String headerTable, List<FieldMap> headerColumnAndValues) {

    }
}
