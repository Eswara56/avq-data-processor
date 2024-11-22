package com.maybank.service;

import com.maybank.config.FileConfig;
import com.maybank.data.FieldMap;
import com.maybank.data.SystemConfigResponse;
import com.maybank.data.UpstreamFieldResponse;
import com.maybank.data.UpstreamResponseData;
import com.maybank.exceptions.NoDataException;
import com.maybank.repository.SystemConfigRepository;
import com.maybank.util.ApplicationUtil;
import com.maybank.util.JobStatusMap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DataProcessor {

    Logger log = LoggerFactory.getLogger(DataProcessor.class);

    private final FileConfig fileConfig;

    private final SystemConfigRepository systemConfigRepository;

    private final DynamicDataService fileDataService;

    private final AuditLogService auditLogService;

    public DataProcessor(FileConfig fileConfig, SystemConfigRepository systemConfigRepository, AuditLogService auditLogService,
                         DynamicDataService fileDataService) {
        this.fileConfig = fileConfig;
        this.systemConfigRepository = systemConfigRepository;
        this.fileDataService = fileDataService;
        this.auditLogService = auditLogService;
    }

    /**
     * This method is used to fetch the system configuration response based on the appCode.
     *
     * @param appCode
     * @return
     */
    public SystemConfigResponse fetchSystemConfigResponse(String appCode) {
        return systemConfigRepository.fetchSystemConfig(appCode);
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
        return response;
    }


    public String getAmountValue(String input) {

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
     * And this is common method for header, detail and trailer data processing.
     *
     * @param appCode
     * @param tableName
     * @param header
     * @param lines
     */
    private void genericDynamicDataProcessor(String appCode, UpstreamResponseData header, List<String> lines, String tableName) {
        Map<String, UpstreamFieldResponse> headerFieldMap = header.getFieldMap();

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
                if (field.getFIELD_NAME().equalsIgnoreCase("CURCY_3_CODE")) {
                }
                System.out.println("Header Line: {}" + value);
                dbRow.add(fieldMap);
            }
            columnAndValues.add(dbRow);
        }
        //compareCreditorAnDebitorBalance(columnAndValues);
        //Dynamically insert data into header table based on the field map
        fileDataService.processData(appCode, fileConfig.getDatabaseType() + "." + tableName, columnAndValues);
    }

    private void compareCreditorAnDebitorBalance(List<List<FieldMap>> headerColumnAndValues) {
        double sum = 0.0;
        double sumCredits = 0.0;
        double sumDebits = 0.0;
        for (List<FieldMap> dbRow : headerColumnAndValues) {
            boolean isCreditor = false;
            boolean isDebitor = false;
            for (FieldMap fieldMap : dbRow) {
                if (fieldMap.getColumn().equalsIgnoreCase("DR_CR_IND")) {
                    if (fieldMap.getValue().contains("C")) {
                        isCreditor = true;
                        isDebitor = false;
                    } else if (fieldMap.getValue().contains("D")) {
                        isDebitor = true;
                        isCreditor = false;
                    } else {
                        log.debug("Invalid value for DR_CR_IND: " + fieldMap.getValue());
                    }
                }
                if (fieldMap.getColumn().equalsIgnoreCase("POST_AMT")) {
                    if (isCreditor) {
                        sumCredits += Double.parseDouble(fieldMap.getValue().replace(",", ""));
                    } else if (isDebitor) {
                        sumDebits += Double.parseDouble(fieldMap.getValue().replace(",", ""));
                    }
                }
            }
        }
        sum = sumCredits - sumDebits;
        System.out.println(" the total sum is" + sum);
        log.debug(" the total sum is" + sum);
        if (sum != 0) {
            auditLogService.log("processData", "Comparing Creditor and Debtor Balances", " UserID: ", "comparing creditor and debtor balances - failed", " Creditor and debtor balances do not tally for AppCode, This a suspense Account " + sum);
            throw new IllegalArgumentException("Creditor and Debitor balance mismatch! This a suspense Account " + sum);
        }
    }
}