package com.maybank.service;

import com.maybank.config.FileConfig;
import com.maybank.data.*;
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

    public DataProcessor(FileConfig fileConfig, SystemConfigRepository systemConfigRepository,
                         DynamicDataService fileDataService) {
        this.fileConfig = fileConfig;
        this.systemConfigRepository = systemConfigRepository;
        this.fileDataService = fileDataService;
    }

    public SystemConfigResponse fetchSystemConfigResponse(String appCode) {
        return systemConfigRepository.fetchSystemConfig(appCode);
    }

    public UpstreamResponseData fetchUpstreamConfigResponse(String systemId, String fileType) {
        return systemConfigRepository.fetchUpstreamConfig(systemId, fileType);
    }

    public String processFilesData(String appCode) {
        String response = "Files processed successfully";
        SystemConfigResponse systemConfig = fetchSystemConfigResponse(appCode);
        if (systemConfig == null) {
            throw new NoDataException("System configuration for '" + appCode + "' not found in database");
        }

        UpstreamConfigResponse upstreamConfig = new UpstreamConfigResponse();
        upstreamConfig.setSystemId(systemConfig.getApplCode());
        //set header data and process header file
        String headerTable = systemConfig.getHeaderTable();
        UpstreamResponseData header = fetchUpstreamConfigResponse(appCode, fileConfig.getHeader());
        upstreamConfig.setHeader(header);
        List<String> headerLines = ApplicationUtil.readFileLines(fileConfig.getHeaderFilePath());
        if(headerLines.isEmpty()){
            throw new NoDataException("No data found in header file");
        }

        //process header file
        genericDynamicDataProcessor(header, headerLines, headerTable);

        //set detail data and process detail file
        String detailTable = systemConfig.getDetailTable();
        UpstreamResponseData detail = fetchUpstreamConfigResponse(appCode, fileConfig.getDetail());
        upstreamConfig.setDetail(detail);
        List<String> detailLines = ApplicationUtil.readFileLines(fileConfig.getDetailFilePath());
        if(detailLines.isEmpty()){
            throw new NoDataException("No data found in detail file");
        }
        //process detail file
        genericDynamicDataProcessor(detail, detailLines, detailTable);

        //set trailer data and process trailer file
        String trailerTable = systemConfig.getTrailerTable();
        UpstreamResponseData trailer = fetchUpstreamConfigResponse(appCode, fileConfig.getTrailer());
        upstreamConfig.setTrailer(trailer);
        List<String> trailerLines = ApplicationUtil.readFileLines(fileConfig.getTrailerFilePath());
        if(trailerLines.isEmpty()){
            throw new NoDataException("No data found in trailer file");
        }
        //process trailer file
        genericDynamicDataProcessor(trailer, trailerLines, trailerTable);

        return response;
    }

    /**
     * This method is used to process the data and store it into the database dynamically.
     * And this is common method for header, detail and trailer data processing.
     * @param systemConfig
     * @param header
     * @param headerLines
     */
    private void genericDynamicDataProcessor(UpstreamResponseData header, List<String> headerLines, String tableName) {
        Map<String, UpstreamFieldResponse> headerFieldMap = header.getFieldMap();
        List<List<FieldMap>> headerColumnAndValues = new ArrayList<>();
        for(String headerLine : headerLines) {
            List<FieldMap> dbRow = new ArrayList<>();
            for (Map.Entry<String, UpstreamFieldResponse> entry : headerFieldMap.entrySet()) {
                FieldMap fieldMap = new FieldMap();
                UpstreamFieldResponse field = entry.getValue();
                String value = headerLine.substring(field.getStart() - 1, field.getEnd());
                fieldMap.setColumn(field.getName());
                fieldMap.setValue("'"+value+"'");
                dbRow.add(fieldMap);
            }
            headerColumnAndValues.add(dbRow);
        }
        //Dynamically insert data into header table based on the field map
        fileDataService.processData(fileConfig.getDatabaseType()+"."+tableName, headerColumnAndValues);
    }




}