package com.maybank.service;

import com.maybank.config.FileConfig;
import com.maybank.data.FieldMap;
import com.maybank.data.SystemConfigResponse;
import com.maybank.data.UpstreamFieldResponse;
import com.maybank.data.UpstreamResponseData;
import com.maybank.exceptions.NoDataException;
import com.maybank.repository.SystemConfigRepository;
import com.maybank.util.ApplicationUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Service
public class DataProcessor {

    private final FileConfig fileConfig;

    private final SystemConfigRepository systemConfigRepository;

    private final DynamicDataService fileDataService;

    private final AuditLogService auditLogService;

    public DataProcessor(FileConfig fileConfig, SystemConfigRepository systemConfigRepository,
                         DynamicDataService fileDataService, AuditLogService auditLogService) {
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
            throw new NoDataException("System configuration for '" + appCode + "' not found in database");
        }


        //set header data and process header file
        //Header----------1

        String fileType = systemConfig.getFileType();

        //String fileExtension = ApplicationUtil.findFileExtension(fileType);

        String headerFilePath = ApplicationUtil.findFullFileName(fileConfig.getBaseFilePath() + "/" + systemConfig.getFilefolder() + "/" + systemConfig.getHeaderFile());
        auditLogService.log("avq-data-processor", "DEBUG", headerFilePath, "Thread-1", "headerFilePath", "");
        System.out.println("header file path" + headerFilePath);
        String headerTable = systemConfig.getHeaderTable();
        UpstreamResponseData header = fetchUpstreamConfigResponse(appCode, fileConfig.getHeader());
        //upstreamConfig.setHeader(header);
        // here , we have to read base directory and file
        List<String> headerLines = ApplicationUtil.readFileLines(headerFilePath);
        if (headerLines.isEmpty()) {
            auditLogService.log("avq-data-processor", "ERROR", "Header Line is Empty", "Thread-1", "headerLines", "");
            throw new NoDataException("No data found in header file");
        }

        //process header file
        genericDynamicDataProcessor(header, headerLines, headerTable);

        //Detail----------------2
        //set detail data and process detail file
        String detailFilePath = ApplicationUtil.findFullFileName(fileConfig.getBaseFilePath() + "/" + systemConfig.getFilefolder() + "/" + systemConfig.getDetailFile());
        auditLogService.log("avq-data-processor", "DEBUG", detailFilePath, "Thread-1", "detailFilePath", "");

        String detailTable = systemConfig.getDetailTable();
        UpstreamResponseData detail = fetchUpstreamConfigResponse(appCode, fileConfig.getDetail());
        //upstreamConfig.setDetail(detail);
        List<String> detailLines = ApplicationUtil.readFileLines(detailFilePath);
        if (detailLines.isEmpty()) {
            auditLogService.log("avq-data-processor", "ERROR", "Detail Line is Empty", "Thread-1", "detailLines", "");
            throw new NoDataException("No data found in detail file");
        }
        //process detail file
        genericDynamicDataProcessor(detail, detailLines, detailTable);

        //Trailer----------------3
        //set trailer data and process trailer file
        String trailerFilePath = ApplicationUtil.findFullFileName(fileConfig.getBaseFilePath() + "/" + systemConfig.getFilefolder() + "/" + systemConfig.getTrailerFile());
        auditLogService.log("avq-data-processor", "DEBUG", trailerFilePath, "Thread-1", "trailerFilePath", "");
        String trailerTable = systemConfig.getTrailerTable();
        UpstreamResponseData trailer = fetchUpstreamConfigResponse(appCode, fileConfig.getTrailer());
        //upstreamConfig.setTrailer(trailer);
        List<String> trailerLines = ApplicationUtil.readFileLines(trailerFilePath);
        if (trailerLines.isEmpty()) {
            auditLogService.log("avq-data-processor", "ERROR", "Trailer Line is Empty", "Thread-1", "trailerLines", "");
            throw new NoDataException("No data found in trailer file");
        }
        //process trailer file
        genericDynamicDataProcessor(trailer, trailerLines, trailerTable);
        return response;
    }


    public  String getAmountValue(String input) {

        input=input.trim();
        if (input.length() < 2) {

            System.out.println("The amount value must be greater that 2 characters length.>"+input
+"<");
            return "";
        }
// Insert a decimal dot at a specific position (e.g., after the 20th character)
        int position = input.length()-2;
// 1-based index for the position before the decimal point
        StringBuilder sb = new StringBuilder(input);
        sb.insert(position, '.');
// Output the result
        String result = sb.toString();
        System.out.println("Original String: " + input);
        System.out.println("Modified String: " + result);
        return result;
    }


    /**
     * This method is used to process the data and store it into the database dynamically.
     * And this is common method for header, detail and trailer data processing.
     *
     * @param tableName
     * @param header
     * @param headerLines
     */
    private void genericDynamicDataProcessor(UpstreamResponseData header, List<String> headerLines, String tableName) {
        Map<String, UpstreamFieldResponse> headerFieldMap = header.getFieldMap();
        List<List<FieldMap>> headerColumnAndValues = new ArrayList<>();
        for (String headerLine : headerLines) {
            List<FieldMap> dbRow = new ArrayList<>();
            FieldMap fieldMap = new FieldMap();
            fieldMap.setColumn("RECORD");
            fieldMap.setValue(headerLine);
            dbRow.add(fieldMap);
            System.out.println("headr Line"+ headerLine);
            for (Map.Entry<String, UpstreamFieldResponse> entry : headerFieldMap.entrySet()) {
                 fieldMap = new FieldMap();
                UpstreamFieldResponse field = entry.getValue();
//                System.out.println("Field type: " + field.getType());
                String value = headerLine.substring(field.getStart() - 1, field.getEnd());
                if ("NUMERIC".equals(field.getType())) {
                    value = value.trim();
                    value = getAmountValue(value);
                    fieldMap.setColumn(field.getName());
                    fieldMap.setValue(value);
                    fieldMap.setType(field.getType());
                }
                else
                {
                    fieldMap.setColumn(field.getName());
                    fieldMap.setValue("'" + value.replace("'", "''") + "'");
                    fieldMap.setType(field.getType());
                }
                dbRow.add(fieldMap);
            } headerColumnAndValues.add(dbRow);
        }

        System.out.println();
        //Dynamically insert data into header table based on the field map
        fileDataService.processData(fileConfig.getDatabaseType() + "." + tableName, headerColumnAndValues);
    }
}