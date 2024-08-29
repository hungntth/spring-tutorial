package com.hungnt.hello_world.exception;

public enum ErrorCode {
    UNCATEGORIZED_EXCEPTION(9999, "Uncategorized error"),
    KEY_INVALID(1001, "Key invalid"),
    USER_EXISTED(1002, "User existed"),
    USERNAME_INVALID(1003, "Username must be at least 3 characters"),
   PASSWORD_INVALID(1004, "Password must be at least 6 characters"),
   USERNAME_NOT_EXSITS(1005, "Username not exists"),
   UNAUTHENTICATED(1006, "Unauthenticated"),

    ;

    ErrorCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    private int code;
    private String message;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
