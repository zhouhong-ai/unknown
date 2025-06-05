package com.example.unknown.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BrowseLogVO {
    /**
     * 登陆用户ID
     */
    private Long loginUserId;
    /**
     * 项目ID
     */
    private Long projectId;
    /**
     * 类型
     */
    private Integer type;
}
