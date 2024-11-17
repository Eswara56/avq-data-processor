package com.maybank.service;

import com.maybank.repository.EGLDetailRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class DetailFileService {

	@Autowired
	private EGLDetailRepository eglDetailRepository;
	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private MetadataService metadataService;

	public void processFilesWithTable(String detailTable, String detailFilePath) {
		// TODO Auto-generated method stub
		
		
		
		
	}

//	public List<Map<String, Object>> processAndMapDetails(BufferedReader reader) {
//		List<Map<String, Object>> dataDetail = new ArrayList<>();
//
//		// Retrieve column names dynamically from the database table
//		Set<String> columnNames = metadataService.getColumnNames("T_EGL_DOWNSTREAM_DETAILED");
//
//		// Retrieve field configurations for mapping
//		List<EGLDetail> detailConfig = processDetails();
//
//		if (detailConfig == null || detailConfig.isEmpty()) {
//			System.out.println("No detail configuration found.");
//			return dataDetail; // Return empty list if no configurations are found
//		}
//
//		try {
//			String line = reader.readLine();
//			while (line != null) {
//				Map<String, Object> record = new HashMap<>();
//				record.put("SYSTEM_ID", "PW");
//				record.put("FILE_TYPE", "DETAIL");
//
//				// Process each field in the detail based on the configuration
//				for (EGLDetail fieldConfig : detailConfig) {
//					String fieldName = fieldConfig.getFIELD_NAME().trim();
//					int start = fieldConfig.getFIELD_START() - 1; // Convert to 0-based index
//					int length = fieldConfig.getFIELD_LENGTH();
//					int end = Math.min(start + length, line.length());
//
//					if (columnNames.contains(fieldName)) {
//						String fieldValue = line.substring(start, end).trim();
//						record.put(fieldName, fieldValue);
//					} else {
//						System.out.println("Skipping non-existent field: " + fieldName);
//					}
//				}
//
//				// Add the processed record to the list
//				dataDetail.add(record);
//				line = reader.readLine(); // Move to the next line
//			}
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		saveAll(dataDetail); // Save all processed records at once
//		insertIntoUpDownMapping(dataDetail);
//		return dataDetail; // Return the list of mapped records
//	}
//
//	public void saveAll(List<Map<String, Object>> dataDetail) {
//		if (dataDetail.isEmpty()) {
//			System.out.println("No detail records to save.");
//			return;
//		}
//
//		// Use the keys from the first record to construct the SQL statement dynamically
//		Map<String, Object> firstRecord = dataDetail.get(0);
//
//		StringBuilder sql = new StringBuilder("INSERT INTO T_EGL_DOWNSTREAM_DETAILED (");
//		StringBuilder valuesPlaceholder = new StringBuilder(" VALUES (");
//
//		for (String columnName : firstRecord.keySet()) {
//			sql.append(columnName).append(", ");
//			valuesPlaceholder.append(":").append(columnName).append(", ");
//		}
//
//		// Remove trailing commas
//		sql.setLength(sql.length() - 2);
//		valuesPlaceholder.setLength(valuesPlaceholder.length() - 2);
//
//		sql.append(")").append(valuesPlaceholder).append(")");
//
//		// Execute batch update
//		jdbcTemplate.batchUpdate(sql.toString(), dataDetail.toArray(new Map[0]));
//
//		System.out.println("Saved " + dataDetail.size() + " records to the database.");
//	}
//	
//	
//	//here save to  data in T_EGL_UP_DOWN_DTL_MAPPING
//
//	private void insertIntoUpDownMapping(List<Map<String, Object>> dataDetail) {
//		// Fetch existing field configurations with FIELD_NAME and FIELD_ORDER
//		// dynamically
//		Set<String> existingFields = fetchExistingFieldConfigs();
//		List<Map<String, Object>> batchData = new ArrayList<>();
//
//		for (Map<String, Object> record : dataDetail) {
//			for (Map.Entry<String, Object> entry : record.entrySet()) {
//				String fieldName = entry.getKey();
//				Object fieldValue = entry.getValue();
//
//				// Get FIELD_ORDER from configuration based on fieldName
//				int fieldOrder = getFieldOrderForFieldName(fieldName);
//
//				// Check if FIELD_NAME and FIELD_ORDER combination already exists
//				String fieldKey = fieldName + "_" + fieldOrder;
//				if (!existingFields.contains(fieldKey)) {
//					// Prepare data for batch insert
//					Map<String, Object> insertData = new HashMap<>();
//
//					// Dynamically populate the fields for insertion
//					insertData.put("SYSTEM_ID", "PW");
//					insertData.put("FILE_TYPE", "DETAIL");
//					insertData.put("FIELD_NAME", fieldName);
//					insertData.put("FIELD_ORDER", fieldOrder);
//					insertData.put("DEFAULT_VALUE", fieldValue);
//
//					// Add the record to the batch list
//					batchData.add(insertData);
//
//					// Mark this combination as inserted to avoid duplicates
//					existingFields.add(fieldKey);
//				}
//			}
//		}
//		// Execute dynamic batch insert
//		if (!batchData.isEmpty()) {
//			saveUpDownMappingData(batchData);
//		}
//	}
//
//	private Set<String> fetchExistingFieldConfigs() {
//		Set<String> existingFields = new HashSet<>();
//
//		Map<String, String> columnNames = getColumnNames("T_EGL_UP_DOWN_DTL_MAPPING");
//		StringBuilder sql = new StringBuilder("SELECT ");
//		sql.append(columnNames.get("FIELD_NAME")).append(", ");
//		sql.append(columnNames.get("FIELD_ORDER"));
//		sql.append(" FROM T_EGL_UP_DOWN_DTL_MAPPING WHERE SYSTEM_ID = 'PW' AND FILE_TYPE = 'DETAIL'");
//
//		jdbcTemplate.query(sql.toString(), rs -> {
//			String fieldName = rs.getString(columnNames.get("FIELD_NAME"));
//			int fieldOrder = rs.getInt(columnNames.get("FIELD_ORDER"));
//			existingFields.add(fieldName + "_" + fieldOrder);
//		});
//
//		return existingFields;
//	}
//
//	/**
//	 * Retrieve column names dynamically from T_EGL_UP_DOWN_DTL_MAPPING.
//	 */
//	private Map<String, String> getColumnNames(String tableName) {
//		Map<String, String> columns = new HashMap<>();
//		try {
//			DatabaseMetaData metaData = jdbcTemplate.getJdbcTemplate().getDataSource().getConnection().getMetaData();
//			ResultSet rs = metaData.getColumns(null, null, tableName, null);
//			while (rs.next()) {
//				String columnName = rs.getString("COLUMN_NAME");
//				columns.put(columnName.toUpperCase(), columnName); // Store column name in uppercase for uniformity
//			}
//		} catch (SQLException e) {
//			e.printStackTrace();
//		}
//		return columns;
//	}
//
//	private int getFieldOrderForFieldName(String fieldName) {
//		// Retrieve the column names dynamically
//		Map<String, String> columnNames = getColumnNames("T_EGL_UP_DOWN_DTL_MAPPING");
//
//		// Construct SQL query dynamically using the retrieved column names
//		StringBuilder sql = new StringBuilder("SELECT ");
//		sql.append(columnNames.get("FIELD_ORDER"));
//		sql.append(" FROM T_EGL_UP_DOWN_DTL_MAPPING WHERE ");
//		sql.append(columnNames.get("SYSTEM_ID")).append(" = :systemId AND ");
//		sql.append(columnNames.get("FILE_TYPE")).append(" = :fileType AND ");
//		sql.append(columnNames.get("FIELD_NAME")).append(" = :fieldName");
//
//		// Set up parameters for the query
//		Map<String, Object> params = new HashMap<>();
//		params.put("systemId", "PW");
//		params.put("fileType", "DETAIL");
//		params.put("fieldName", fieldName);
//
//		// Execute query to get FIELD_ORDER dynamically
//		Integer fieldOrder = jdbcTemplate.queryForObject(sql.toString(), params, Integer.class);
//
//		// Return -1 if FIELD_ORDER is not found, otherwise return the retrieved
//		// FIELD_ORDER
//		return (fieldOrder != null) ? fieldOrder : -1;
//	}
//
//	private void saveUpDownMappingData(List<Map<String, Object>> upDownMappedData) {
//		if (upDownMappedData.isEmpty()) {
//			System.out.println("No data to save in T_EGL_UP_DOWN_DTL_MAPPING.");
//			return;
//		}
//
//		// Dynamically construct the SQL INSERT statement
//		Map<String, Object> firstRecord = upDownMappedData.get(0);
//
//		StringBuilder sql = new StringBuilder("INSERT INTO T_EGL_UP_DOWN_DTL_MAPPING (");
//		StringBuilder valuesPlaceholder = new StringBuilder(" VALUES (");
//
//		for (String columnName : firstRecord.keySet()) {
//			sql.append(columnName).append(", ");
//			valuesPlaceholder.append(":").append(columnName).append(", ");
//		}
//
//		// Remove trailing commas and spaces
//		sql.setLength(sql.length() - 2);
//		valuesPlaceholder.setLength(valuesPlaceholder.length() - 2);
//		sql.append(")").append(valuesPlaceholder).append(")");
//
//		// Execute batch update with the dynamically created SQL statement
//		jdbcTemplate.batchUpdate(sql.toString(), upDownMappedData.toArray(new Map[0]));
//
//		System.out.println("Saved " + upDownMappedData.size() + " records to T_EGL_UP_DOWN_DTL_MAPPING.");
//	}
//
//	public List<EGLDetail> processDetails() {
//		String systemId = "PW";
//		String fileType = "DETAIL";
//
//		List<EGLDetail> eglDetailList = eglDetailRepository.findBySystemIdAndFileTypeOrderByFieldOrder(systemId,
//				fileType);
//
//		if (eglDetailList == null || eglDetailList.isEmpty()) {
//			System.out.println(
//					"No field configurations found for SYSTEM_ID = " + systemId + " and FILE_TYPE = " + fileType);
//		}
//		return eglDetailList;
//	}

}
