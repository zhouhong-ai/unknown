package com.example.unknown.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.unknown.domain.BrowseLogInfo;
import com.example.unknown.domain.LoginUerVo;
import com.example.unknown.enums.BrowseTypeEnum;
import com.example.unknown.service.BrowseLogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/browseLog")
public class BrowseLogInfoController {

    @Autowired
    private BrowseLogInfoService browseLogInfoService;

    @PostMapping("/insert")
    public void insert(Long loginUserId, Long projectId, Integer type) {
        if (BrowseTypeEnum.PROJECT.getCode() == type) {
            browseLogInfoService.insertBrowseProjectLog(loginUserId, projectId);
        }
    }

    @GetMapping("/queryPageByProjectId")
    public Page<LoginUerVo> queryPageByProjectId(Long projectId, Page<BrowseLogInfo> page) {
        return browseLogInfoService.queryPageByProjectId(projectId, page);
    }

    @GetMapping("/projectBrowseCount")
    public int projectBrowseCount(Long projectId) {
        return browseLogInfoService.projectBrowseCount(projectId);
    }
}
