package com.maybank.controller;

import com.maybank.data.*;
import com.maybank.service.DataProcessor;
import com.maybank.service.UnitCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
public class DataProcessorController {
    @Autowired
   private UnitCodeService unitCodeMapService;
    private final DataProcessor dataProcessor;

    public DataProcessorController(DataProcessor dataProcessor) {
        this.dataProcessor = dataProcessor;
    }


    @GetMapping("/systemConfig")
    public ResponseEntity<SystemConfigResponse> fetchSystemConfig(@RequestParam  String applCode) {
        return new ResponseEntity<>(dataProcessor.fetchSystemConfigResponse(applCode), HttpStatus.OK);
    }

    @GetMapping("/upstreamConfig")
    public ResponseEntity<UpstreamResponseData> fetchUpstreamConfig(@RequestParam String systemId, @RequestParam String fileType) {
        return new ResponseEntity<>(dataProcessor.fetchUpstreamConfigResponse(systemId, fileType), HttpStatus.OK);
    }

    @GetMapping("/processFiles")
    public ResponseEntity<String> processFiles(@RequestParam String applCode) {
        return new ResponseEntity<>(dataProcessor.processFilesData(applCode), HttpStatus.OK);
    }



    @GetMapping("/unit-code-maps")
    public Map<String, UnitCodeMap> getUnitCodeMaps() {
        // Fetch the map from the service layer
        return unitCodeMapService.fetchUnitCodeMap();
    }

    @GetMapping("/AC-code-maps")
    public Map<String, AcCodeMap> getAcCodeMaps() {
        // Fetch the map from the service layer
        return unitCodeMapService.fetchAcCodeMap();
    }
    @GetMapping("/co-code-maps")
    public Map<CompanyCodeKey, CompanyCode> getComCodeMaps() {
        // Fetch the map from the service layer
        return unitCodeMapService.fetchCompanyCodeMap();
    }


}
