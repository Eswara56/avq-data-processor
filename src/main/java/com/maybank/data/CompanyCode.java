package com.maybank.data;
// T_EGL_COMPANY_CODE_MAP
public class CompanyCode {

    private String COM_DESC;
    private String EGL_ENT_NAME;
    private Character IS_ENABLED;
    private Integer COMPANY_CODE_ID;
    private String STAGE;

    public CompanyCode(String COM_DESC, String EGL_ENT_NAME, Character IS_ENABLED, Integer COMPANY_CODE_ID, String STAGE) {
        this.COM_DESC = COM_DESC;
        this.EGL_ENT_NAME = EGL_ENT_NAME;
        this.IS_ENABLED = IS_ENABLED;
        this.COMPANY_CODE_ID = COMPANY_CODE_ID;
        this.STAGE = STAGE;
    }

    public String getCOM_DESC() {
        return COM_DESC;
    }

    public void setCOM_DESC(String COM_DESC) {
        this.COM_DESC = COM_DESC;
    }

    public String getEGL_ENT_NAME() {
        return EGL_ENT_NAME;
    }

    public void setEGL_ENT_NAME(String EGL_ENT_NAME) {
        this.EGL_ENT_NAME = EGL_ENT_NAME;
    }

    public Character getIS_ENABLED() {
        return IS_ENABLED;
    }

    public void setIS_ENABLED(Character IS_ENABLED) {
        this.IS_ENABLED = IS_ENABLED;
    }

    public Integer getCOMPANY_CODE_ID() {
        return COMPANY_CODE_ID;
    }

    public void setCOMPANY_CODE_ID(Integer COMPANY_CODE_ID) {
        this.COMPANY_CODE_ID = COMPANY_CODE_ID;
    }

    public String getSTAGE() {
        return STAGE;
    }

    public void setSTAGE(String STAGE) {
        this.STAGE = STAGE;
    }
}
