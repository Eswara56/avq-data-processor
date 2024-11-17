package com.maybank.controller;

import com.maybank.data.SystemConfigResponse;
import com.maybank.data.UpstreamResponseData;
import com.maybank.service.DataProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class DataProcessorController {

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
}
