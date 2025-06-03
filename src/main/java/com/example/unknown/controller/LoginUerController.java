package com.example.unknown.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.unknown.domain.LoginUer;
import com.example.unknown.domain.LoginUerVo;
import com.example.unknown.service.LoginUerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/loginUser")
public class LoginUerController {

    @Autowired
    private LoginUerService loginUerService;

    @GetMapping("/queryPage")
    public Page<LoginUerVo> queryPage(LoginUerVo vo, Page<LoginUer> page) {
        return loginUerService.queryPage(vo, page);
    }
}
