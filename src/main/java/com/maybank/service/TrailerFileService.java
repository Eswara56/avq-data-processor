package org.maybank.com.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.maybank.com.Repository.EGLHeaderRepository;
import org.maybank.com.Repository.EGLTrailerRepository;
import org.maybank.com.entity.EGLHeader;
import org.maybank.com.entity.EGLTrailer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

@Service
public class TrailerFileService {

	@Autowired
	private NamedParameterJdbcTemplate jdbcTemplate;

	@Autowired
	private EGLTrailerRepository eglTrailerRepository;

	@Autowired
	private MetadataService metadataService;

	public void processAndMapTrailers(BufferedReader reader) {
		Set<String> columnNames = metadataService.getColumnNames("T_EGL_DOWNSTREAM_TRAILER"); // Assuming a trailer
																								// table
		List<EGLTrailer> trailerConfig = processTrailer(); // Renamed to reflect trailer processing

		if (trailerConfig == null || trailerConfig.isEmpty()) {
			System.out.println("No trailer configuration found.");
			return;
		}

		Map<String, Object> record = new HashMap<>();

		try {
			String line = reader.readLine();
			if (line != null) {
				record.put("SYSTEM_ID", "PW");
				record.put("FILE_TYPE", "TRAILER");

				for (EGLTrailer fieldConfig : trailerConfig) {
					String fieldName = fieldConfig.getFIELD_NAME().trim();
					int start = fieldConfig.getFIELD_START() - 1; // Convert to 0-based index
					int length = fieldConfig.getFIELD_LENGTH();
					int end = start + length;

					// Ensure that start and end are within the bounds of the line
					if (start < 0 || start >= line.length()) {
						System.out.println("Skipping field " + fieldName + " due to invalid start index: " + start);
						continue;
					}
					if (end > line.length()) {
						System.out.println("Adjusting end index for field " + fieldName + " to match line length.");
						end = line.length();
					}

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

		saveTrailer(record); // Save to trailer-specific table or process differently if needed
	}

	private void saveTrailer(Map<String, Object> record) {
		if (record.isEmpty()) {
			System.out.println("No trailer record to save.");
			return;
		}

		// Construct dynamic SQL INSERT statement based on column names
		StringBuilder sql = new StringBuilder("INSERT INTO T_EGL_DOWNSTREAM_TRAILER (");
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

		System.out.println("Saved trailer record to the database dynamically.");
	}

	public List<EGLTrailer> processTrailer() {
		String systemId = "PW";
		String fileType = "TRAILER";

		// Retrieve a single header configuration based on SYSTEM_ID and FILE_TYPE
		List<EGLTrailer> egltrailer = eglTrailerRepository.findBySystemIdAndFileType(systemId, fileType);

		if (egltrailer == null) {
			System.out.println(
					"No field configuration found for SYSTEM_ID = " + systemId + " and FILE_TYPE = " + fileType);
		}
		return egltrailer;
	}

}
