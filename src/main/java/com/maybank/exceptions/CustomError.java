package com.maybank.exceptions;

import lombok.Data;

@Data
public class CustomError {
    private String code;
    private String message;
}
