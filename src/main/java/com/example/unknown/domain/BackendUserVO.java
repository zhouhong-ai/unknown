package com.example.unknown.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BackendUserVO {
    /**
     * 主键ID，自增
     */
    private Long id;
    /**
     * 账户
     */
    private String account;
}
