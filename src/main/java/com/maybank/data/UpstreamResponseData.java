package com.maybank.data;

import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Setter
@Getter
public class UpstreamResponseData {
    private String fileType;
    private Map<String, UpstreamFieldResponse> fieldMap;
}
