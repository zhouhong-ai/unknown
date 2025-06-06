package com.example.unknown.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.unknown.domain.ProjectVO;
import com.example.unknown.service.ProjectInfoService;
import com.example.unknown.service.ProjectPeopleRelationService;
import com.example.unknown.utils.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/project")
public class ProjectController {

    @Autowired
    private ProjectInfoService projectInfoService;

    @Autowired
    private ProjectPeopleRelationService projectPeopleRelationService;

    @PostMapping("/createOrUpdate")
    public Boolean createOrUpdate(@Validated @RequestBody ProjectVO vo) {
        String userName = ContextUtil.getUserName();
        return projectInfoService.createOrUpdate(vo.buildProjectInfo(userName));
    }

    @GetMapping("queryById")
    public ProjectVO queryById(Long id) {
        return projectInfoService.queryById(id);
    }

    @GetMapping("/queryPage")
    public Page<ProjectVO> queryPage(String name , Integer status, long current, long size) {
        ProjectVO vo = new ProjectVO();
        vo.setName(name);
        vo.setStatus(status);
        return projectInfoService.queryPage(vo, new Page<>(current, size));
    }

    @GetMapping("/statusClassStatic")
    public Map<String, Long> statusClassStatic() {
        return projectInfoService.statusClassStatic();
    }

    @PostMapping("/updateStatus")
    public Boolean updateStatus(@RequestBody ProjectVO vo) {
        String userName = ContextUtil.getUserName();
        return projectInfoService.updateStatus(vo.getId(), vo.getStatus(), userName);
    }

    @DeleteMapping("/delete")
    public Boolean delete(Long id) {
        String userName = ContextUtil.getUserName();
        return projectInfoService.delete(id, userName);
    }

    @PostMapping("/batchAddPeople")
    public Boolean batchAddPeople(@RequestBody ProjectVO vo) {
        String userName = ContextUtil.getUserName();
        return projectPeopleRelationService.batchAddPeople(vo.getId(), vo.getPeopleIds(), userName);
    }

    @PostMapping("/batchDeletePeople")
    public Boolean batchDeletePeople(@RequestBody ProjectVO vo) {
        String userName = ContextUtil.getUserName();
        return projectPeopleRelationService.batchDeletePeople(vo.getId(), vo.getPeopleIds(), userName);
    }
}
