package com.example.unknown.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.unknown.dao.BackendUserDao;
import com.example.unknown.domain.BackendUser;
import com.example.unknown.enums.AppCode;
import com.example.unknown.enums.YnEnum;
import com.example.unknown.exception.APIException;
import com.example.unknown.service.BackendUserService;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service(value = "backendUserService")
public class BackendUserServiceImpl extends ServiceImpl<BackendUserDao, BackendUser> implements BackendUserService {

    @Override
    public void validateUser(String account, String password) {
        BackendUser backendUser = this.getOne(new LambdaQueryWrapper<BackendUser>()
                .eq(BackendUser::getAccount, account).eq(BackendUser::getYn, YnEnum.YES.getCode()));
        if (Objects.isNull(backendUser)) {
            throw new APIException(AppCode.APP_ERROR, "用户不存在");
        }
        if (!backendUser.getPassword().equals(password)) {
            throw new APIException(AppCode.APP_ERROR, "密码错误");
        }
    }

    @Override
    public BackendUser getByAccount(String account) {
        BackendUser backendUser = this.getOne(new LambdaQueryWrapper<BackendUser>()
                .eq(BackendUser::getAccount, account).eq(BackendUser::getYn, YnEnum.YES.getCode()));
        if (Objects.isNull(backendUser)) {
            throw new APIException(AppCode.APP_ERROR, "用户不存在");
        }
        return backendUser;
    }
}
