package com.maybank.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

/**
 * Class to hold the response from upstream system for header, detail and trailer
 */
@Setter
@Getter
public class UpstreamConfigResponse {
    private String systemId;
    private UpstreamResponseData header;
    private UpstreamResponseData detail;
    private UpstreamResponseData trailer;
}
