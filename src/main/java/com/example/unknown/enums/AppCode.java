package com.example.unknown.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum AppCode implements StatusCode {

    APP_ERROR(400, "业务异常");

    private final int code;

    private final String msg;
}
