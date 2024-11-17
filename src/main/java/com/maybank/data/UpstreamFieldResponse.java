package com.maybank.data;

import lombok.Getter;
import lombok.Setter;

/**
 * Class to hold the response from upstream system
 */
@Setter
@Getter
public class UpstreamFieldResponse {
    private String type;
    private String name;
    private int start;
    private int end;
    private int length;
    private int order;
}
