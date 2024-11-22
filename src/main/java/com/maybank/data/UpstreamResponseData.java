package com.maybank.data;

import java.util.Map;


public class UpstreamResponseData {
    private String FILE_TYPE;
    private Map<String, UpstreamFieldResponse> fieldMap;

    public String getFILE_TYPE() {
        return FILE_TYPE;
    }

    public void setFILE_TYPE(String FILE_TYPE) {
        this.FILE_TYPE = FILE_TYPE;
    }

    public Map<String, UpstreamFieldResponse> getFieldMap() {
        return fieldMap;
    }

    public void setFieldMap(Map<String, UpstreamFieldResponse> fieldMap) {
        this.fieldMap = fieldMap;
    }
}

