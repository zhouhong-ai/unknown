package com.example.unknown.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ProjectStatusEnum {

    NO_START(1, "未开始"),

    RECRUIT(2, "招募中"),

    END(3, "已结束");


    private final int code;

    private final String value;
}
