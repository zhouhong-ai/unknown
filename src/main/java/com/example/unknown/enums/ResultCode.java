package com.example.unknown.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ResultCode implements StatusCode {

    SUCCESS(200, "请求成功"),

    FAILED(400, "请求失败"),

    VALIDATE_ERROR(401, "参数校验失败"),

    RESPONSE_PACK_ERROR(402, "response返回包装失败");

    private final int code;

    private final String msg;
}
