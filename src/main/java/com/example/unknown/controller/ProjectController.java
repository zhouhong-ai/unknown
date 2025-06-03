package com.example.unknown.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.unknown.domain.ProjectInfo;
import com.example.unknown.domain.ProjectVO;
import com.example.unknown.service.ProjectInfoService;
import com.example.unknown.utils.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/project")
public class ProjectController {

    @Autowired
    private ProjectInfoService projectInfoService;

    @PostMapping("/createOrUpdate")
    public Boolean createOrUpdate(@Validated @RequestBody ProjectVO vo) {
        String userName = ContextUtil.getUserName();
        return projectInfoService.createOrUpdate(vo.buildProjectInfo(userName));
    }

    @GetMapping("/queryPage")
    public Page<ProjectVO> queryPage(@RequestBody ProjectVO vo, Page<ProjectInfo> page) {
        return projectInfoService.queryPage(vo, page);
    }

    @GetMapping("/statusClassStatic")
    public Map<Integer, Long> statusClassStatic() {
        return projectInfoService.statusClassStatic();
    }

    @PostMapping("/updateStatus")
    public Boolean updateStatus(Long id, Integer status) {
        String userName = ContextUtil.getUserName();
        return projectInfoService.updateStatus(id, status, userName);
    }

    @DeleteMapping("/delete")
    public Boolean delete(Long id) {
        String userName = ContextUtil.getUserName();
        return projectInfoService.delete(id, userName);
    }
}
