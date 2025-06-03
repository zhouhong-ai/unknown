package com.example.unknown.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.unknown.domain.PeopleInfo;
import com.example.unknown.domain.PeopleVo;
import com.example.unknown.domain.ProjectPeopleRelation;
import com.example.unknown.service.PeopleInfoService;
import com.example.unknown.service.PeoplePropertyService;
import com.example.unknown.utils.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/people")
public class PeopleController {

    @Autowired
    private PeopleInfoService peopleInfoService;

    @Autowired
    private PeoplePropertyService peoplePropertyService;

    @PostMapping("/createOrUpdate")
    public Boolean createOrUpdate(@RequestBody PeopleVo vo) {
        String userName = ContextUtil.getUserName();
        return peopleInfoService.createOrUpdate(vo.buildPeopleInfo(userName));
    }

    @GetMapping("/queryPage")
    public Page<PeopleVo> queryPage(@RequestBody PeopleVo vo, Page<PeopleInfo> page) {
        return peopleInfoService.queryPage(vo, page);
    }

    @GetMapping("/queryProjectPeoplePage")
    public Page<PeopleVo> queryProjectPeoplePage(Long projectId, Page<ProjectPeopleRelation> page) {
        return peopleInfoService.queryProjectPeoplePage(projectId, page);
    }

    @DeleteMapping("/delete")
    public Boolean delete(Long id) {
        String userName = ContextUtil.getUserName();
        return peopleInfoService.delete(id, userName);
    }

    @PostMapping("/updatePropertyShow")
    public Boolean updatePropertyShow(Long id, Integer show) {
        String userName = ContextUtil.getUserName();
        return peoplePropertyService.updatePropertyShow(id, show, userName);
    }
}
