package com.example.unknown.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.unknown.domain.BackendUser;

public interface BackendUserService extends IService<BackendUser> {
    /**
     * 用户账号密码验证
     */
    void validateUser(String account, String password);
    /**
     * 根据账号获取用户信息
     */
    BackendUser getByAccount(String account);
}
