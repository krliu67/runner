package com.example.exception;

public enum ErrorType {
    UNKNOWN_ERROR(-1, "未知错误"),
    LOGIN_ERROR(1, "登录失败，用户名或密码错误"),
    REQUEST_ARGS_ERROR(2, "传入参数为空"),
    PASSWORD_CHECK_ERROR(3, "旧密码不正确"),
    TOKEN_CHECK_ERROR(4, "用户token校验失败"),
    REGISTER_ERROR_HAD(5, "用户名已存在"),
    USER_ID_NON_ERROR(6, "用户ID参数必传"),
    USERNAME_NON_ERROR(7, "用户名参数必传"),
    PASSWORD_NON_ERROR(8, "用户密码参数必传"),
    PASSWORD_NEW_NON_ERROR(9, "用户新密码参数必传"),
    TOKEN_NON_ERROR(10, "token参数必传"),
    PROFILE_NON_ERROR(11, "个人简介参数必传"),
    NOTE_TITLE_NON_ERROR(12, "标题参数必传"),
    NOTE_CONTENT_NON_ERROR(13, "内容参数必传"),
    NOTE_ID_NON_ERROR(14, "noteId参数必传"),
    USER_NOT_EXIST(15, "该用户不存在"),
    UPLOAD_RUNDATA_FAIL(16,"上传跑步数据失败"),
    TODAY_NO_RUN(17,"今天没有运动哦"),
    YESTERDAY_NO_RUN(18,"昨天没有运动哦");
    private Integer code;

    private String message;

    ErrorType(Integer code, String message) {
        this.code = code;
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
