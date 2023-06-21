package com.example.exception;

import lombok.Data;

@Data
public class DiyException extends RuntimeException{
    private Integer code;
    public DiyException(ErrorType errorType) {
        super(errorType.getMessage());
        this.code = errorType.getCode();
    }
}
