package org.maybank.com.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.maybank.com.Repository.EGLHeaderRepository;
import org.maybank.com.entity.EGLHeader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;


@Service
public class HeaderFileService {

	
	
 
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private EGLHeaderRepository eglHeaderRepository;

	@Autowired
	private MetadataService metadataService;



	 public void processAndMapHeaders(BufferedReader reader) {
	        // Retrieve column names dynamically from the database table
	        Set<String> columnNames = metadataService.getColumnNames("T_EGL_DOWNSTREAM_HEADER");

	        Set<String> baseTablecolumnNames = metadataService.getColumnNames("T_EGL_INTRF_HDR_DLY");
	        // Retrieve field configurations for mapping
	        List<EGLHeader> headerConfig = processHeader();

	        if (headerConfig == null || headerConfig.isEmpty()) {
	            System.out.println("No header configuration found.");
	            return;
	        }

	        Map<String, Object> record = new HashMap<>();
	        Map<String, Object> record1 = new HashMap<>();

	        try {
	            String line = reader.readLine();
	            if (line != null) {
	                // Manually set SYSTEM_ID and FILE_TYPE for the HEADER record
	                record.put("SYSTEM_ID", "PW");
	                record.put("FILE_TYPE", "HEADER");

	                // Process each field in the header based on the configuration
	                for (EGLHeader fieldConfig : headerConfig) {
	                    String fieldName = fieldConfig.getFIELD_NAME().trim();
	                    int start = fieldConfig.getFIELD_START() - 1; // Convert to 0-based index
	                    int length = fieldConfig.getFIELD_LENGTH();
	                    int end = Math.min(start + length, line.length());
	                    
	                    // Only process baseTablecolumnNames fields that exist in the column metadata
	                    if (baseTablecolumnNames.contains(fieldName)) {
	                        String fieldValue = line.substring(start, end).trim();
	                        record1.put(fieldName, fieldValue);
	                    } else {
	                        System.out.println("Skipping non-existent field: " + fieldName);
	                    }
	                    
	                    // Only process fields that exist in the column metadata
	                    if (columnNames.contains(fieldName)) {
	                        String fieldValue = line.substring(start, end).trim();
	                        record.put(fieldName, fieldValue);
	                    } else {
	                        System.out.println("Skipping non-existent field: " + fieldName);
	                    }
	                }
	            }

	            reader.close();
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
//	        Save the single header record to the base database T_EGL_INTRF_AQ_HDR_DLY Table
             saveBaseSytemHeaderTable(record1);
	        // Save the single header record to the database T_EGL_DOWNSTREAM_HEADER table
	        saveHeader(record);
	    }

	    private void saveBaseSytemHeaderTable(Map<String, Object> record) {
	    	if (record.isEmpty()) {
	            System.out.println("No header record to save.");
	            return;
	        }

	        // Construct dynamic SQL INSERT statement based on column names
	        StringBuilder sql = new StringBuilder("INSERT INTO T_EGL_INTRF_HDR_DLY (");
	        StringBuilder valuesPlaceholder = new StringBuilder(" VALUES (");

	        for (String columnName : record.keySet()) {
	            sql.append(columnName).append(", ");
	            valuesPlaceholder.append(":").append(columnName).append(", ");
	        }

	        // Remove trailing commas
	        sql.setLength(sql.length() - 2);
	        valuesPlaceholder.setLength(valuesPlaceholder.length() - 2);

	        sql.append(")").append(valuesPlaceholder).append(")");

	        // Insert the record into the database
	        jdbcTemplate.update(sql.toString(), record);

	        System.out.println("Saved header record to the database dynamically.");
	    	
	
		
	}

		private void saveHeader(Map<String, Object> record) {
	        if (record.isEmpty()) {
	            System.out.println("No header record to save.");
	            return;
	        }

	        // Construct dynamic SQL INSERT statement based on column names
	        StringBuilder sql = new StringBuilder("INSERT INTO T_EGL_DOWNSTREAM_HEADER (");
	        StringBuilder valuesPlaceholder = new StringBuilder(" VALUES (");

	        for (String columnName : record.keySet()) {
	            sql.append(columnName).append(", ");
	            valuesPlaceholder.append(":").append(columnName).append(", ");
	        }

	        // Remove trailing commas
	        sql.setLength(sql.length() - 2);
	        valuesPlaceholder.setLength(valuesPlaceholder.length() - 2);

	        sql.append(")").append(valuesPlaceholder).append(")");

	        // Insert the record into the database
	        jdbcTemplate.update(sql.toString(), record);

	        System.out.println("Saved header record to the database dynamically.");
	    }


public List<EGLHeader> processHeader() {
    String systemId = "PW";
    String fileType = "HEADER";

    // Retrieve a single header configuration based on SYSTEM_ID and FILE_TYPE
    List<EGLHeader> eglHeader = eglHeaderRepository.findBySystemIdAndFileType(systemId, fileType);

    if (eglHeader == null) {
        System.out.println("No field configuration found for SYSTEM_ID = " + systemId + " and FILE_TYPE = " + fileType);
    }
    return eglHeader;
}


	 

	  
	

}
