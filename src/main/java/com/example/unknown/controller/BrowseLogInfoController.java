package com.example.unknown.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.unknown.domain.BrowseLogVO;
import com.example.unknown.domain.LoginUerVo;
import com.example.unknown.enums.BrowseTypeEnum;
import com.example.unknown.service.BrowseLogInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/browseLog")
public class BrowseLogInfoController {

    @Autowired
    private BrowseLogInfoService browseLogInfoService;

    @PostMapping("/insert")
    public void insert(@RequestBody BrowseLogVO vo) {
        if (BrowseTypeEnum.PROJECT.getCode() == vo.getType()) {
            browseLogInfoService.insertBrowseProjectLog(vo.getLoginUserId(), vo.getProjectId());
        }
    }

    @GetMapping("/queryPageByProjectId")
    public Page<LoginUerVo> queryPageByProjectId(Long projectId, long current, long size) {
        return browseLogInfoService.queryPageByProjectId(projectId, new Page<>(current, size));
    }

    @GetMapping("/projectBrowseCount")
    public int projectBrowseCount(Long projectId) {
        return browseLogInfoService.projectBrowseCount(projectId);
    }
}
