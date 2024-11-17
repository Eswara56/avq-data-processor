package com.maybank.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

/**
 * This class to map the data from table: T_EGL_BASE_SYSTEMS
 */
@Setter
@Getter
public class SystemConfigResponse {

    private String applCode;

    private String headerFile;

    private String detailFile;

    private String footerFile;
    
    private String headerTable;
    
    private String detailTable;
    
    private String trailerTable;
}