package com.sidihuo.pivottable.exception;

/**
 * @Description
 * @Date 2023/7/25 19:12
 * @Created by yanggangjie
 */
public class PivotTableException extends RuntimeException {

    private String code;
    private String message;

    public PivotTableException() {
    }

    public PivotTableException(String code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
        this.message = message;
    }

    public PivotTableException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}

