package com.example.unknown.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.unknown.domain.PeopleVo;
import com.example.unknown.service.PeopleInfoService;
import com.example.unknown.service.PeoplePropertyService;
import com.example.unknown.utils.ContextUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/people")
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

    @GetMapping("/queryById")
    public PeopleVo queryById(Long id) {
        return peopleInfoService.queryById(id);
    }

    @GetMapping("/queryPage")
    public Page<PeopleVo> queryPage(Long id, String nickname, long current, long size) {
        PeopleVo vo = new PeopleVo();
        vo.setId(id);
        vo.setNickname(nickname);
        return peopleInfoService.queryPage(vo, new Page<>(current, size));
    }

    @GetMapping("/queryProjectPeoplePage")
    public Page<PeopleVo> queryProjectPeoplePage(Long projectId, long current, long size) {
        return peopleInfoService.queryProjectPeoplePage(projectId, new Page<>(current, size));
    }

    @GetMapping("/queryPeoplePageFileProjectPeople")
    public Page<PeopleVo> queryPeoplePageFileProjectPeople(Long id, String nickname, Long projectId, long current, long size) {
        return peopleInfoService.queryPeoplePageFileProjectPeople(id, nickname, projectId, new Page<>(current, size));
    }

    @DeleteMapping("/delete")
    public Boolean delete(Long id) {
        String userName = ContextUtil.getUserName();
        return peopleInfoService.delete(id, userName);
    }

    @PostMapping("/updatePropertyShow")
    public Boolean updatePropertyShow(@RequestBody PeopleVo vo) {
        String userName = ContextUtil.getUserName();
        return peoplePropertyService.updatePropertyShow(vo.getPropertyId(), vo.getShow(), userName);
    }
}
