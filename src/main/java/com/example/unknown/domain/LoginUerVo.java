package com.example.unknown.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginUerVo {
    /**
     * 主键ID，自增
     */
    private Long id;
    /**
     * 主键ID集合
     */
    private List<Long> ids;
    /**
     * 手机号
     */
    private String phone;
    /**
     * 用户昵称
     */
    private String nickname;
    /**
     * 最后登陆时间
     */
    private Date lastLoginTime;
}
