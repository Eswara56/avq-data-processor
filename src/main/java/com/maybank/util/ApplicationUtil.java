package com.maybank.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
public class ApplicationUtil {

    /**
     * This method reads the file and returns the lines in the file
     * @param filePath
     * @return
     */
    public static List<String> readFileLines(String filePath) {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        } catch (IOException e) {
            log.error("Error reading file: {}", e.getMessage());
        }
        return lines;
    }

    public static String findFileExtension(String fileType) {
        String extension = "";
        if (StringUtils.isNotBlank(fileType) && fileType.equalsIgnoreCase("AS400")) {
            extension = ".dat";
        }
        return extension;
    }
}
