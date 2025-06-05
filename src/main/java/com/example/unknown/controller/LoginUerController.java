package com.example.unknown.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.unknown.domain.LoginUer;
import com.example.unknown.domain.LoginUerVo;
import com.example.unknown.service.LoginUerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/loginUser")
public class LoginUerController {

    @Autowired
    private LoginUerService loginUerService;

    @GetMapping("/queryPage")
    public Page<LoginUerVo> queryPage(String phone, String nickname, Page<LoginUer> page) {
        LoginUerVo vo = new LoginUerVo();
        vo.setPhone(phone);
        vo.setNickname(nickname);
        return loginUerService.queryPage(vo, page);
    }
}
