package com.example.unknown.controller;

import cn.hutool.core.util.StrUtil;
import com.example.unknown.domain.AuthVO;
import com.example.unknown.service.BackendUserService;
import com.example.unknown.utils.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private BackendUserService backendUserService;

    @PostMapping("/login")
    public String login(@RequestBody AuthVO vo) {
        backendUserService.validateUser(vo.getAccount(), vo.getPassword());
        String token = jwtTokenUtil.generateToken(vo.getAccount());
        if (StrUtil.isNotBlank(token)) {
            return String.format("Bearer %s", token);
        }
        return null;

    }
}
