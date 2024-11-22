package com.maybank.util;

import com.maybank.repository.DynamicDataRepository;
import com.maybank.service.AuditLogService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

//@Slf4j
public class ApplicationUtil {
    private static final Logger log = LoggerFactory.getLogger(ApplicationUtil.class);
    /**
     * This method reads the file and returns the lines in the file
     *
     * @param filePath
     * @return
     */

    private static AuditLogService auditLogService = null;

    public ApplicationUtil(AuditLogService auditLogService) {
        this.auditLogService = auditLogService;
    }

    public static List<String> readFileLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            log.debug("Error reading file: {}", e.getMessage());
        }
        return lines;
    }

    public static String findFileExtension(String fileType) {
        String extension = "";
        if (StringUtils.isNotBlank(fileType) && fileType.equalsIgnoreCase("AS400")) {
            extension = ".dat";
        } else {
            extension = ".dat";
        }
        return extension;
    }

    public static String findFullFileName(String filePath) {
        String getFileNameeWithouExtension = filePath.substring(filePath.lastIndexOf("/") + 1);
        String fulleFilePath = filePath.substring(0, filePath.lastIndexOf("/"));
        //Find all the file names in the folder
        File folder = new File(fulleFilePath);
        File[] listOfFiles = folder.listFiles();
        String fileName = "";
        assert listOfFiles != null;
        for (File file : listOfFiles) {
            if (file.isFile()) {
                if (file.getName().contains(getFileNameeWithouExtension)) {
                    fileName = file.getAbsolutePath();
                }
            }
        }
        log.debug("File Name: {}", fileName);
        return fileName;
    }
}