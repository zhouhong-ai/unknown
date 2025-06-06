package com.example.unknown.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.unknown.domain.BrowseLogVO;
import com.example.unknown.domain.PeopleVo;
import com.example.unknown.domain.ProjectVO;
import com.example.unknown.enums.BrowseTypeEnum;
import com.example.unknown.service.BrowseLogInfoService;
import com.example.unknown.service.PeopleInfoService;
import com.example.unknown.service.ProjectInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/wx")
public class WXController {

    @Autowired
    private ProjectInfoService projectInfoService;

    @Autowired
    private PeopleInfoService peopleInfoService;

    @Autowired
    private BrowseLogInfoService browseLogInfoService;

    @GetMapping("/queryPage")
    public Page<ProjectVO> queryPage(String name , Integer status, long current, long size) {
        ProjectVO vo = new ProjectVO();
        vo.setName(name);
        vo.setStatus(status);
        return projectInfoService.queryPage(vo, new Page<>(current, size));
    }

    @GetMapping("/queryProjectPeoplePage")
    public Page<PeopleVo> queryProjectPeoplePage(Long projectId, long current, long size) {
        return peopleInfoService.queryProjectPeoplePage(projectId, new Page<>(current, size));
    }

    @PostMapping("/insertBrowseLog")
    public void insert(@RequestBody BrowseLogVO vo) {
        if (BrowseTypeEnum.PROJECT.getCode() == vo.getType()) {
            browseLogInfoService.insertBrowseProjectLog(vo.getLoginUserId(), vo.getProjectId());
        }
    }
}
