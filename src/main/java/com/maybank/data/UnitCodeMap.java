package com.maybank.data;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

//T_EGL_UNIT_CODE_MAP

public class UnitCodeMap {

    private String EGL_RC_SEG2;

    private String UNIT_CODE_DESC;

    private String RC_DESC;

    private Integer UNIT_CODE_ID;

    private String STAGE;

    public UnitCodeMap( String EGL_RC_SEG2, String UNIT_CODE_DESC, String RC_DESC, Integer UNIT_CODE_ID, String STAGE) {
        this.EGL_RC_SEG2 = EGL_RC_SEG2;
        this.UNIT_CODE_DESC = UNIT_CODE_DESC;
        this.RC_DESC = RC_DESC;
        this.UNIT_CODE_ID = UNIT_CODE_ID;
        this.STAGE = STAGE;
    }



    public String getEGL_RC_SEG2() {
        return EGL_RC_SEG2;
    }

    public void setEGL_RC_SEG2(String EGL_RC_SEG2) {
        this.EGL_RC_SEG2 = EGL_RC_SEG2;
    }

    public String getUNIT_CODE_DESC() {
        return UNIT_CODE_DESC;
    }

    public void setUNIT_CODE_DESC(String UNIT_CODE_DESC) {
        this.UNIT_CODE_DESC = UNIT_CODE_DESC;
    }

    public String getRC_DESC() {
        return RC_DESC;
    }

    public void setRC_DESC(String RC_DESC) {
        this.RC_DESC = RC_DESC;
    }

    public Integer getUNIT_CODE_ID() {
        return UNIT_CODE_ID;
    }

    public void setUNIT_CODE_ID(Integer UNIT_CODE_ID) {
        this.UNIT_CODE_ID = UNIT_CODE_ID;
    }

    public String getSTAGE() {
        return STAGE;
    }

    public void setSTAGE(String STAGE) {
        this.STAGE = STAGE;
    }
}
