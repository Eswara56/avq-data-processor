package com.maybank.data;

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

    private String trailerFile;
    
    private String headerTable;
    
    private String detailTable;
    
    private String trailerTable;

    private String filefolder;

    private String fileType;
}