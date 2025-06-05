package com.example.unknown.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.unknown.domain.PeopleInfo;
import com.example.unknown.domain.PeopleVo;
import com.example.unknown.domain.ProjectPeopleRelation;

public interface PeopleInfoService extends IService<PeopleInfo> {
    /**
     * 用户创建
     */
    Boolean createOrUpdate(PeopleInfo peopleInfo);
    /**
     * 根据ID获取详情
     */
    PeopleVo queryById(Long id);
    /**
     * 分页查询
     */
    Page<PeopleVo> queryPage(PeopleVo vo, Page<PeopleInfo> page);
    /**
     * 用户删除
     */
    Boolean delete(Long id, String userName);
    /**
     * 分页查询项目参与人员
     */
    Page<PeopleVo> queryProjectPeoplePage(Long projectId, Page<ProjectPeopleRelation> page);
    /**
     * 分页查询用户列表，且过滤当前项目已参与人员
     */
    Page<PeopleVo> queryPeoplePageFileProjectPeople(Long id, String nickname, Long projectId, Page<PeopleInfo> page);
}
