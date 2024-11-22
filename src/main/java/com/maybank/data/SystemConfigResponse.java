package com.maybank.data;

/**
 * This class to map the data from table: T_EGL_BASE_SYSTEMS
 */
//T_EGL_BASE_SYSTEMS
public class SystemConfigResponse {

    private String APPL_CODE;
    private String HEADER_FILE;

    private String DETAIL_FILE;

    private String FOOTER_FILE;

    private String TBL_HDR;

    private String TBL_DTL;

    private String BL_TRLR;

    private String FILE_FOLDER;
    private String FILE_TYPE;

    public String getAPPL_CODE() {
        return APPL_CODE;
    }

    public void setAPPL_CODE(String APPL_CODE) {
        this.APPL_CODE = APPL_CODE;
    }

    public String getHEADER_FILE() {
        return HEADER_FILE;
    }

    public void setHEADER_FILE(String HEADER_FILE) {
        this.HEADER_FILE = HEADER_FILE;
    }

    public String getDETAIL_FILE() {
        return DETAIL_FILE;
    }

    public void setDETAIL_FILE(String DETAIL_FILE) {
        this.DETAIL_FILE = DETAIL_FILE;
    }

    public String getFOOTER_FILE() {
        return FOOTER_FILE;
    }

    public void setFOOTER_FILE(String FOOTER_FILE) {
        this.FOOTER_FILE = FOOTER_FILE;
    }

    public String getTBL_HDR() {
        return TBL_HDR;
    }

    public void setTBL_HDR(String TBL_HDR) {
        this.TBL_HDR = TBL_HDR;
    }

    public String getTBL_DTL() {
        return TBL_DTL;
    }

    public void setTBL_DTL(String TBL_DTL) {
        this.TBL_DTL = TBL_DTL;
    }

    public String getBL_TRLR() {
        return BL_TRLR;
    }

    public void setBL_TRLR(String BL_TRLR) {
        this.BL_TRLR = BL_TRLR;
    }

    public String getFILE_FOLDER() {
        return FILE_FOLDER;
    }

    public void setFILE_FOLDER(String FILE_FOLDER) {
        this.FILE_FOLDER = FILE_FOLDER;
    }

    public String getFILE_TYPE() {
        return FILE_TYPE;
    }

    public void setFILE_TYPE(String FILE_TYPE) {
        this.FILE_TYPE = FILE_TYPE;
    }

    @Override
    public String toString() {
        return "SystemConfigResponse{" +
                "APPL_CODE='" + APPL_CODE + '\'' +
                ", HEADER_FILE='" + HEADER_FILE + '\'' +
                ", DETAIL_FILE='" + DETAIL_FILE + '\'' +
                ", FOOTER_FILE='" + FOOTER_FILE + '\'' +
                ", TBL_HDR='" + TBL_HDR + '\'' +
                ", TBL_DTL='" + TBL_DTL + '\'' +
                ", BL_TRLR='" + BL_TRLR + '\'' +
                ", FILE_FOLDER='" + FILE_FOLDER + '\'' +
                ", FILE_TYPE='" + FILE_TYPE + '\'' +
                '}';
    }
}