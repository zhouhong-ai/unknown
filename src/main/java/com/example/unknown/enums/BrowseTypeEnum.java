package com.example.unknown.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum BrowseTypeEnum {

    PROJECT(1, "项目");

    private final int code;

    private final String value;
}
