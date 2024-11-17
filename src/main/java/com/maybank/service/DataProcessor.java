package com.maybank.service;

import com.maybank.config.FileConfig;
import com.maybank.data.SystemConfigResponse;
import com.maybank.data.UpstreamConfigResponse;
import com.maybank.data.UpstreamFieldResponse;
import com.maybank.data.UpstreamResponseData;
import com.maybank.exceptions.NoDataException;
import com.maybank.repository.SystemConfigRepository;
import com.maybank.util.ApplicationUtil;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class DataProcessor {

    private final FileConfig fileConfig;

    private final SystemConfigRepository systemConfigRepository;

    private final DetailFileService detailFileService;
    private final HeaderFileService headerFileService;
    private final TrailerFileService trailerFileService;

    public DataProcessor(FileConfig fileConfig, SystemConfigRepository systemConfigRepository,
                         DetailFileService detailFileService, HeaderFileService headerFileService,
                         TrailerFileService trailerFileService) {
        this.fileConfig = fileConfig;
        this.systemConfigRepository = systemConfigRepository;
        this.detailFileService = detailFileService;
        this.headerFileService = headerFileService;
        this.trailerFileService = trailerFileService;
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
        UpstreamResponseData header = fetchUpstreamConfigResponse(systemConfig.getHeaderTable(), fileConfig.getHeader());
        upstreamConfig.setHeader(header);
        List<String> headerLines = ApplicationUtil.readFileLines(fileConfig.getHeaderFilePath());
        if(headerLines.isEmpty()){
            throw new NoDataException("No data found in header file");
        }
        String healderLine = headerLines.get(0);
        Map<String, UpstreamFieldResponse> headerFieldMap = header.getFieldMap();



        //set detail data and process detail file
        UpstreamResponseData detail = fetchUpstreamConfigResponse(systemConfig.getDetailTable(), fileConfig.getDetail());
        upstreamConfig.setDetail(detail);
        List<String> detailLines = ApplicationUtil.readFileLines(fileConfig.getDetailFilePath());
        if(detailLines.isEmpty()){
            throw new NoDataException("No data found in detail file");
        }

        //set trailer data and process trailer file
        UpstreamResponseData trailer = fetchUpstreamConfigResponse(systemConfig.getDetailTable(), fileConfig.getTrailer());
        upstreamConfig.setTrailer(trailer);
        List<String> trailerLines = ApplicationUtil.readFileLines(fileConfig.getTrailerFilePath());
        if(trailerLines.isEmpty()){
            throw new NoDataException("No data found in trailer file");
        }
        String trailerLine = trailerLines.get(0);

        return response;
    }


//    public void processFiles(String applCode) {
//        SystemConfig systemConfig = systemConfigRepository.findByApplCode(applCode);
//        if (systemConfig == null) {
//            throw new RuntimeException("System configuration for '" + applCode + "' not found in database");
//        }
//        // Retrieve paths for each file type
//        String baseDirectory = "D:/Work/AvqFiles/"; // Base directory where files are located
//        String headerFilePath = baseDirectory + systemConfig.getHeaderFile();
//        String detailFilePath = baseDirectory + systemConfig.getDetailFile();
//        String trailerFilePath = baseDirectory + systemConfig.getFooterFile();
//        System.out.println("Header File Path: " + headerFilePath);
//        System.out.println("Detail File Path: " + detailFilePath);
//        System.out.println("Footer File Path: " + trailerFilePath);
//        String detailTable = systemConfig.getTBL_DTL();
//        String headerTable = systemConfig.getTBL_HDR();
//        String TrailerTable = systemConfig.getTBL_TRLR();
//        System.out.println();
//        //procees each file and table respected
//        detailFileService.processFilesWithTable(detailTable, detailFilePath);
//    }

}