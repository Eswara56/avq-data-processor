package com.maybank.data;

/**
 * Class to hold the response from upstream system
 */

//T_EGL_UPSTREAM_CONFIG
public class UpstreamFieldResponse {
    private String FILED_TYPE;
    private String FIELD_NAME;
    private int FIELD_START;
    private int FIELD_END;
    private int FIELD_LENGTH;
    private int UP_STREAM_ORDER;

    public String getFILED_TYPE() {
        return FILED_TYPE;
    }

    public void setFILED_TYPE(String FILED_TYPE) {
        this.FILED_TYPE = FILED_TYPE;
    }

    public String getFIELD_NAME() {
        return FIELD_NAME;
    }

    public void setFIELD_NAME(String FIELD_NAME) {
        this.FIELD_NAME = FIELD_NAME;
    }

    public int getFIELD_START() {
        return FIELD_START;
    }

    public void setFIELD_START(int FIELD_START) {
        this.FIELD_START = FIELD_START;
    }

    public int getFIELD_END() {
        return FIELD_END;
    }

    public void setFIELD_END(int FIELD_END) {
        this.FIELD_END = FIELD_END;
    }

    public int getFIELD_LENGTH() {
        return FIELD_LENGTH;
    }

    public void setFIELD_LENGTH(int FIELD_LENGTH) {
        this.FIELD_LENGTH = FIELD_LENGTH;
    }

    public int getUP_STREAM_ORDER() {
        return UP_STREAM_ORDER;
    }

    public void setUP_STREAM_ORDER(int UP_STREAM_ORDER) {
        this.UP_STREAM_ORDER = UP_STREAM_ORDER;
    }
}
