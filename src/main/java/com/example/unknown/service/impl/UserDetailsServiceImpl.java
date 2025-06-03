package com.example.unknown.service.impl;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.example.unknown.domain.BackendUser;
import com.example.unknown.service.BackendUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Objects;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private BackendUserService backendUserService;

    @Override
    public UserDetails loadUserByUsername(String account) throws UsernameNotFoundException {
        // 这里应该从数据库中获取用户信息
        if (StrUtil.isNotBlank(account)) {
            BackendUser user = backendUserService.getOne(new LambdaQueryWrapper<BackendUser>().eq(BackendUser::getAccount, account));
            if (Objects.nonNull(user)) {
                return new User(user.getAccount(), passwordEncoder.encode(user.getPassword()), new ArrayList<>());
            }
        }
        throw new UsernameNotFoundException("User not found with username: " + account);
    }
}
