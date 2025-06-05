package com.example.unknown.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.HashMap;
import java.util.Map;

@Getter
@AllArgsConstructor
public enum ProjectStatusEnum {

    NO_START(1, "未开始", "noStart"),

    RECRUIT(2, "招募中", "recruit"),

    END(3, "已结束", "end");


    private final int code;

    private final String value;

    private final String desc;

    /**
     * 静态映射
     */
    private static final Map<Integer, ProjectStatusEnum> MAP = new HashMap<>();

    static {
        for (ProjectStatusEnum typeEnum : ProjectStatusEnum.values()) {
            MAP.put(typeEnum.getCode(), typeEnum);
        }
    }

    /**
     * projectStatusEnum
     */
    public static ProjectStatusEnum projectStatusEnum(Integer type) {
        return MAP.get(type);
    }
}
