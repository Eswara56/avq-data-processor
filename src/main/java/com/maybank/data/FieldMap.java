package com.maybank.data;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * This class holds the column name and
 */
@Setter
@Getter
@ToString

public class FieldMap {
    private String column;
    private String value;
    private  String type;
}
