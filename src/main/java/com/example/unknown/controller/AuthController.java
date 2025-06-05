package com.example.unknown.controller;

import cn.hutool.core.util.StrUtil;
import com.example.unknown.domain.AuthVO;
import com.example.unknown.domain.BackendUser;
import com.example.unknown.domain.BackendUserVO;
import com.example.unknown.enums.AppCode;
import com.example.unknown.exception.APIException;
import com.example.unknown.service.BackendUserService;
import com.example.unknown.utils.AliOssUtil;
import com.example.unknown.utils.ContextUtil;
import com.example.unknown.utils.JwtTokenUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api")
public class AuthController {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private BackendUserService backendUserService;

    @Autowired
    private AliOssUtil aliOssUtil;

    @PostMapping("/login")
    public String login(@RequestBody AuthVO vo) {
        backendUserService.validateUser(vo.getAccount(), vo.getPassword());
        String token = jwtTokenUtil.generateToken(vo.getAccount());
        if (StrUtil.isNotBlank(token)) {
            return String.format("Bearer %s", token);
        }
        return null;
    }

    @GetMapping("/getUserDetail")
    public BackendUserVO getUserDetail() {
        String userName = ContextUtil.getUserName();
        BackendUser backendUser = backendUserService.getByAccount(userName);
        BackendUserVO vo = new BackendUserVO();
        BeanUtils.copyProperties(backendUser, vo);
        return vo;
    }

    @PostMapping("/upload")
    public String upload(MultipartFile file) {
        try {
            //原始文件名字
            String originalFilename = file.getOriginalFilename();
            //截取原始文件名后缀
            assert originalFilename != null;
            int lastIndexOf = originalFilename.lastIndexOf(".");
            String extension = originalFilename.substring(lastIndexOf);
            //构造新文件的名称
            String objectName = UUID.randomUUID() + extension;
            return aliOssUtil.upload(file.getBytes(), objectName);
        } catch (IOException e) {
            throw new APIException(AppCode.APP_ERROR, "文件上传失败!");
        }
    }
}
