package com.example.unknown.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum YnEnum {

    NO(0),

    YES(1);

    private final int code;
}
