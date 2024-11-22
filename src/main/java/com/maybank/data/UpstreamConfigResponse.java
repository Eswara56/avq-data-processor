package com.maybank.data;

/**
 * Class to hold the response from upstream system for header, detail and trailer
 */
//T_EGL_UPSTREAM_CONFIG
public class UpstreamConfigResponse {
    private String SYSTEM_ID;
    private UpstreamResponseData header;
    private UpstreamResponseData detail;
    private UpstreamResponseData trailer;

    public String getSYSTEM_ID() {
        return SYSTEM_ID;
    }

    public void setSYSTEM_ID(String SYSTEM_ID) {
        this.SYSTEM_ID = SYSTEM_ID;
    }

    public UpstreamResponseData getHeader() {
        return header;
    }

    public void setHeader(UpstreamResponseData header) {
        this.header = header;
    }

    public UpstreamResponseData getDetail() {
        return detail;
    }

    public void setDetail(UpstreamResponseData detail) {
        this.detail = detail;
    }

    public UpstreamResponseData getTrailer() {
        return trailer;
    }

    public void setTrailer(UpstreamResponseData trailer) {
        this.trailer = trailer;
    }
}
