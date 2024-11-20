package com.maybank.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "file")
public class FileConfig {

    private String header;
    private String detail;
    private String trailer;
    private String baseFilePath;
    private String headerFilePath;
    private String detailFilePath;
    private String trailerFilePath;
    private String databaseType;
    private int threadCount;
    private int batchSize;
    private String baseFolderPath;

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getTrailer() {
        return trailer;
    }

    public void setTrailer(String trailer) {
        this.trailer = trailer;
    }

    public String getBaseFilePath() {
        return baseFilePath;
    }

    public void setBaseFilePath(String baseFilePath) {
        this.baseFilePath = baseFilePath;
    }

    public String getHeaderFilePath() {
        return headerFilePath;
    }

    public void setHeaderFilePath(String headerFilePath) {
        this.headerFilePath = headerFilePath;
    }

    public String getDetailFilePath() {
        return detailFilePath;
    }

    public void setDetailFilePath(String detailFilePath) {
        this.detailFilePath = detailFilePath;
    }

    public String getTrailerFilePath() {
        return trailerFilePath;
    }

    public void setTrailerFilePath(String trailerFilePath) {
        this.trailerFilePath = trailerFilePath;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public int getThreadCount() {
        return threadCount;
    }

    public void setThreadCount(int threadCount) {
        this.threadCount = threadCount;
    }

    public int getBatchSize() {
        return batchSize;
    }

    public void setBatchSize(int batchSize) {
        this.batchSize = batchSize;
    }

    public String getBaseFolderPath() {
        return baseFolderPath;
    }

    public void setBaseFolderPath(String baseFolderPath) {
        this.baseFolderPath = baseFolderPath;
    }


}
