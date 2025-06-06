package com.example.unknown.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.unknown.domain.LoginUerVo;
import com.example.unknown.service.BrowseLogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/browseLog")
public class BrowseLogInfoController {

    @Autowired
    private BrowseLogInfoService browseLogInfoService;

    @GetMapping("/queryPageByProjectId")
    public Page<LoginUerVo> queryPageByProjectId(Long projectId, long current, long size) {
        return browseLogInfoService.queryPageByProjectId(projectId, new Page<>(current, size));
    }

    @GetMapping("/projectBrowseCount")
    public int projectBrowseCount(Long projectId) {
        return browseLogInfoService.projectBrowseCount(projectId);
    }
}
