package com.maybank.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
@Setter
@Getter
public class FileConfig {
    private String header;
    private String detail;
    private String trailer;
    private String headerFilePath;
    private String detailFilePath;
    private String trailerFilePath;
    private String databaseType;
}
