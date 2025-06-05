package com.example.unknown.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.unknown.domain.ProjectPeopleRelation;

import java.util.List;
import java.util.Map;

public interface ProjectPeopleRelationService extends IService<ProjectPeopleRelation> {
    /**
     * 批量添加项目用户关系
     */
    void batchAdd(Long projectId, List<Long> peopleIds, String userName);
    /**
     * 查询项目对应参与人数
     */
    Map<Long, Long> queryPeopleNumByProjectIds(List<Long> projectIds);
    /**
     * 分页查询
     */
    Page<ProjectPeopleRelation> queryByProjectIdPage(Long projectId, Page<ProjectPeopleRelation> page);
    /**
     * 批量删除
     */
    void batchDelete(Long projectId, String userName);
}
