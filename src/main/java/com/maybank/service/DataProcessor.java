package com.maybank.service;

import com.maybank.config.FileConfig;
import com.maybank.data.*;
import com.maybank.entity.JobStatus;
import com.maybank.exceptions.BusinessException;
import com.maybank.exceptions.NoDataException;
import com.maybank.repository.SystemConfigRepository;
import com.maybank.util.ApplicationUtil;
import com.maybank.util.JobStatusMap;
import com.maybank.util.Status;
import com.maybank.util.StatusMessageType;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

@Service
public class DataProcessor {

    Logger log = LoggerFactory.getLogger(DataProcessor.class);

    private final FileConfig fileConfig;

    private final SystemConfigRepository systemConfigRepository;

    private final DynamicDataService fileDataService;

    private final AuditLogService auditLogService;

    private JobStatusAyncService jobStatusAyncService;

    public DataProcessor(FileConfig fileConfig, SystemConfigRepository systemConfigRepository, AuditLogService auditLogService,
                         DynamicDataService fileDataService, JobStatusAyncService jobStatusAyncService) {
        this.fileConfig = fileConfig;
        this.systemConfigRepository = systemConfigRepository;
        this.fileDataService = fileDataService;
        this.auditLogService = auditLogService;
        this.jobStatusAyncService = jobStatusAyncService;
    }

    /**
     * This method is used to fetch the system configuration response based on the appCode.
     *
     * @param appCode
     * @return
     */
    public SystemConfigResponse fetchSystemConfigResponse(String appCode) {
        SystemConfigResponse systemConfigResponse = systemConfigRepository.fetchSystemConfig(appCode);
        jobStatusAyncService.updateJobStatusMapAndInsertIntoDB(appCode, new Status(StatusMessageType.COMPLETED.getValue(), 10.0),
                new JobStatus(ApplicationUtil.currentMethodName(), appCode, "CONFIG", 0, ApplicationUtil.currentMethodName(),
                        "System configuration fetched successfully", null));
        return systemConfigResponse;
    }

    /**
     * This method is used to fetch the upstream configuration response based on the systemId and fileType.
     *
     * @param systemId
     * @param fileType
     * @return
     */
    public UpstreamResponseData fetchUpstreamConfigResponse(String systemId, String fileType) {
        return systemConfigRepository.fetchUpstreamConfig(systemId, fileType);
    }

    /**
     * This method is used to process the files data and store it into the database.
     *
     * @param appCode
     * @return
     */
    public String processFilesData(String appCode) {
        long startTime = System.currentTimeMillis();
        JobStatusMap.createSystemCodeMap(appCode);
        String response = "Files processed successfully";
        SystemConfigResponse systemConfig = fetchSystemConfigResponse(appCode);
        if (systemConfig == null) {
            auditLogService.log("processFilesData", "Fetching system configuration ", "USER_ID" + appCode, "system configuration not found", "System configuration for application code " + appCode + "not found in BASE_SYSTEM Table");
            throw new NoDataException(" System configuration for application code - '" + appCode + " not found in BASE_SYSTEM table");

        }
        //set header data and process header file
        //Header----------1
        String fileType = systemConfig.getFILE_TYPE();
        String headerFilePath = null;
        try {
            headerFilePath = ApplicationUtil.findFullFileName(fileConfig.getBaseFilePath() + "/" + systemConfig.getFILE_FOLDER() + "/" + systemConfig.getHEADER_FILE());

        } catch (Exception e) {
            auditLogService.log("processFilesData", "Header File Validator", "UserID: " + appCode, "Header file path is invalid", "Header file path is invalid for application code " + appCode + "" + e.getMessage());
            throw new NoDataException("Error occurred while finding header file path for application code " + appCode);
        }

        System.out.println("header file path" + headerFilePath);

        String headerTable = systemConfig.getTBL_HDR();
        UpstreamResponseData header = fetchUpstreamConfigResponse(appCode, fileConfig.getHeader());
        //upstreamConfig.setHeader(header);
        // here , we have to read base directory and file
        List<String> headerLines = ApplicationUtil.readFileLines(headerFilePath);
        if (headerLines.isEmpty()) {
            auditLogService.log("processFilesData", "Header File Validator", " UserID: " + appCode, "Header file processing failed", "Header file for application code " + appCode + " is empty or contains no data. File path:" + headerFilePath);
            throw new NoDataException("Header file for application code " + appCode + " is empty or contains no data. File path:" + headerFilePath);
        }
        try {
            //process header file
            genericDynamicDataProcessor(appCode, header, headerLines, headerTable);
        } catch (StringIndexOutOfBoundsException e) {
            auditLogService.log("genericDynamicDataProcessor", "Header File Validator", "UserID: " + appCode, "Header file processing failed", " Unable to process the header file: The required data is missing or incomplete in the specified range " + e.getMessage() + "Please check the file for errors.");
            throw new RuntimeException(e);
        }

        //Detail----------------2
        //set detail data and process detail file
        String detailFilePath = ApplicationUtil.findFullFileName(fileConfig.getBaseFilePath() + "/" + systemConfig.getFILE_FOLDER() + "/" + systemConfig.getDETAIL_FILE());
        String detailTable = systemConfig.getTBL_DTL();
        UpstreamResponseData detail = fetchUpstreamConfigResponse(appCode, fileConfig.getDetail());
        List<String> detailLines = ApplicationUtil.readFileLines(detailFilePath);
        if (detailLines.isEmpty()) {
            auditLogService.log("processFilesData", "Detail File Validator", "UserID: " + appCode, "DETAIL file processing failed", "Header file for application code " + appCode + " is empty or contains no data. File path:" + detailFilePath);
            throw new NoDataException("Header file for application code " + appCode + " is empty or contains no data. File path:" + detailFilePath);
        }

        //process detail file
        try {
            //process detail file
            genericDynamicDataProcessor(appCode, detail, detailLines, detailTable);

        } catch (StringIndexOutOfBoundsException e) {
            auditLogService.log("genericDynamicDataProcessor", "Detail File Validator", "UserID: " + appCode, "Detail file processing failed", " Unable to process the detail file: The required data is missing or incomplete in the specified range " + e.getMessage() + "Please check the file for errors.");
            throw new RuntimeException(e);
        }
        //Trailer----------------3
        //set trailer data and process trailer file
        String trailerFilePath = ApplicationUtil.findFullFileName(fileConfig.getBaseFilePath() + "/" + systemConfig.getFILE_FOLDER() + "/" + systemConfig.getFOOTER_FILE());
        System.out.println("trailer file path" + trailerFilePath);
        String trailerTable = systemConfig.getBL_TRLR();
        UpstreamResponseData trailer = fetchUpstreamConfigResponse(appCode, fileConfig.getTrailer());
        List<String> trailerLines = ApplicationUtil.readFileLines(trailerFilePath);
        if (trailerLines.isEmpty()) {
            auditLogService.log("processFilesData", "Trailer File Validator", "UserID: " + appCode, "Trailer file processing failed", "Trailer file for application code " + appCode + "is empty or contains no data. File path:" + trailerFilePath);
            throw new NoDataException("Header file for application code " + appCode + " is empty or contains no data. File path:" + trailerFilePath);
        }
        //process trailer file
        try {
            genericDynamicDataProcessor(appCode, trailer, trailerLines, trailerTable);
        } catch (StringIndexOutOfBoundsException e) {
            auditLogService.log("genericDynamicDataProcessor", "Trailer File Validator", "UserID: " + appCode, "Trailer file processing failed", " Unable to process the Trailer file: The required data is missing or incomplete in the specified range " + e.getMessage() + "Please check the file for errors.");
            throw new RuntimeException(e);
        }
        long endTime = System.currentTimeMillis();
        long durationInSeconds = (endTime - startTime) / 1000;
        System.out.println("Execution time: " + durationInSeconds + " seconds");
        return response;
    }


    private String getAmountValue(String input) {
        input = input.trim();
        if (input.length() < 2) {
            return "";
        }
// Insert a decimal dot at a specific position (e.g., after the 20th character)
        int position = input.length() - 2;
// 1-based index for the position before the decimal point
        StringBuilder sb = new StringBuilder(input);
        sb.insert(position, '.');
// Output the result
        String result = sb.toString();
        log.debug("Original String: {}" + input);
        log.debug("Modified String: {}" + result);
        return result;
    }


    /**
     * This method is used to process the data and store it into the database dynamically.
     * And this is common method for upstreamResponseData, detail and trailer data processing.
     *
     * @param appCode
     * @param tableName
     * @param upstreamResponseData
     * @param lines
     */
    private void genericDynamicDataProcessor(String appCode, UpstreamResponseData upstreamResponseData, List<String> lines, String tableName) {
        Map<String, UpstreamFieldResponse> headerFieldMap = upstreamResponseData.getFieldMap();

        List<List<FieldMap>> columnAndValues = new ArrayList<>();
        for (String line : lines) {
            List<FieldMap> dbRow = new ArrayList<>();
            FieldMap fieldMap = new FieldMap();
            fieldMap.setColumn("RECORD");
            fieldMap.setValue(line);
            dbRow.add(fieldMap);
            System.out.println("Header Line: {}" + line);
            for (Map.Entry<String, UpstreamFieldResponse> entry : headerFieldMap.entrySet()) {
                fieldMap = new FieldMap();
                UpstreamFieldResponse field = entry.getValue();
                System.out.println("Field type: {}" + field.getFILED_TYPE());
                String value = line.substring(field.getFIELD_START() - 1, field.getFIELD_END());
                if ("NUMERIC".equals(field.getFILED_TYPE())) {
                    value = value.trim();
                    value = getAmountValue(value);
                    fieldMap.setColumn(field.getFIELD_NAME());
                    fieldMap.setValue(value);
                    fieldMap.setType(field.getFILED_TYPE());
                } else {
                    fieldMap.setColumn(field.getFIELD_NAME());
                    fieldMap.setValue("'" + value.replace("'", "''") + "'");
                    fieldMap.setType(field.getFILED_TYPE());
                }
                System.out.println("Header Line: {}" + value);
                dbRow.add(fieldMap);
            }
            List<FieldMap> sortedDBRow = dbRow.parallelStream().sorted(Comparator.comparing(FieldMap::getColumn)).toList();
            columnAndValues.add(sortedDBRow);
        }

        //Dynamically insert data into upstreamResponseData table based on the field map
        fileDataService.processData(appCode, fileConfig.getDatabaseType() + "." + tableName, columnAndValues);
        if (upstreamResponseData.getFILE_TYPE().equalsIgnoreCase("DETAIL")) {
            creditAndDebitTally(columnAndValues);
        }
    }

    //
    private void creditAndDebitTally(List<List<FieldMap>> columnAndValues) {
// Nested Map to group by CO_CODE -> CURCY_3_CODE -> UNIT_NO -> DR_CR_IND
        Map<CreditAndDebit, BigDecimal> result = new HashMap<>();
        BigDecimal totalCredit = BigDecimal.ZERO;
        BigDecimal totalDebit = BigDecimal.ZERO;
        for (List<FieldMap> dbRow : columnAndValues) {
            //double postAmt = 0.0;
            BigDecimal postAmt = BigDecimal.ZERO;
            CreditAndDebit creditAndDebit = new CreditAndDebit();
            String DR_CR_IND = "";
            for (FieldMap eachColumn : dbRow) {
                if (eachColumn.getColumn().replace("'", "").equals("CO_CODE")) {
                    creditAndDebit.setCO_CODE(eachColumn.getValue().replace("'", ""));
                } else if (eachColumn.getColumn().replace("'", "").trim().equals("CURCY_3_CODE")) {
                    creditAndDebit.setCURCY_3_CODE(eachColumn.getValue().replace("'", ""));
                } else if (eachColumn.getColumn().replace("'", "").equals("UNIT_NO")) {
                    creditAndDebit.setUNIT_NO(Integer.parseInt(eachColumn.getValue().replace("'", "")));
                } else if (eachColumn.getColumn().replace("'", "").equals("DR_CR_IND")) {
                    DR_CR_IND = eachColumn.getValue().replace("'", "");
                } else if (eachColumn.getColumn().replace("'", "").equals("POST_AMT")) {
                    postAmt = new BigDecimal(eachColumn.getValue().replace("'", ""));
                }
            }
            if (StringUtils.isNotBlank(DR_CR_IND) && DR_CR_IND.equals("C")) {
                totalCredit = totalCredit.add(postAmt);
                result.put(creditAndDebit, result.getOrDefault(creditAndDebit, BigDecimal.ZERO).add(postAmt));
            } else if (StringUtils.isNotBlank(DR_CR_IND) && DR_CR_IND.equals("D")) {
                totalDebit = totalDebit.add(postAmt);
                result.put(creditAndDebit, result.getOrDefault(creditAndDebit, BigDecimal.ZERO).subtract(postAmt));
            }
        }
        System.out.println("Total Credit: " + totalCredit);
        System.out.println("Total Debit: " + totalDebit);
        //If any of the object in the map has other than 0.0 value, then log the error
        result.entrySet().stream().filter(entry -> entry.getValue().compareTo(BigDecimal.ZERO) != 0).forEach(entry -> {
            auditLogService.log("creditAndDebitTally", "Credit and Debit Tally", "UserID: " + entry.getKey().getCO_CODE(), "Credit and Debit Tally failed", "Credit and Debit Tally failed for CO_CODE: " + entry.getKey().getCO_CODE() + " CURCY_3_CODE: " + entry.getKey().getCURCY_3_CODE() + " UNIT_NO: " + entry.getKey().getUNIT_NO() + " POST_AMT: " + entry.getValue());
            //throw new BusinessException("Credit and Debit Tally failed for CO_CODE: " + entry.getKey().getCO_CODE() + " CURCY_3_CODE: " + entry.getKey().getCURCY_3_CODE() + " UNIT_NO: " + entry.getKey().getUNIT_NO() + " POST_AMT: " + entry.getValue());
        });
    }
}