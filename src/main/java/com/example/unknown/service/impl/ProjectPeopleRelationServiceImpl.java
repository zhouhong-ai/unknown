package com.example.unknown.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.unknown.dao.ProjectPeopleRelationDao;
import com.example.unknown.domain.ProjectPeopleRelation;
import com.example.unknown.enums.AppCode;
import com.example.unknown.enums.YnEnum;
import com.example.unknown.exception.APIException;
import com.example.unknown.service.ProjectPeopleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = "projectPeopleRelationService")
public class ProjectPeopleRelationServiceImpl extends ServiceImpl<ProjectPeopleRelationDao, ProjectPeopleRelation> implements ProjectPeopleRelationService {

    @Autowired
    private ProjectPeopleRelationDao projectPeopleRelationDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(Long projectId, List<Long> peopleIds, String userName) {
        List<ProjectPeopleRelation> list = this.list(new LambdaQueryWrapper<ProjectPeopleRelation>()
                .eq(ProjectPeopleRelation::getProjectId, projectId).eq(ProjectPeopleRelation::getYn, YnEnum.YES.getCode()));
        if (CollectionUtil.isNotEmpty(list)) {
            // 先删除历史数据
            this.lambdaUpdate().eq(ProjectPeopleRelation::getProjectId, projectId)
                    .set(ProjectPeopleRelation::getModifyAt, Calendar.getInstance().getTime())
                    .set(ProjectPeopleRelation::getModifyName, userName)
                    .set(ProjectPeopleRelation::getYn, YnEnum.NO.getCode()).update();
        }
        // 添加关系
        this.batchAddPeople(projectId, peopleIds, userName);
    }

    @Override
    public Map<Long, Long> queryPeopleNumByProjectIds(List<Long> projectIds) {
        Map<Long, Long> map = new HashMap<>();

        QueryWrapper<ProjectPeopleRelation> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("project_id", projectIds);
        queryWrapper.eq("yn", YnEnum.YES.getCode());
        queryWrapper.groupBy("project_id");
        queryWrapper.select("project_id", "count(1) AS count");
        List<Map<String, Object>> maps = projectPeopleRelationDao.selectMaps(queryWrapper);
        if (CollectionUtil.isNotEmpty(maps)) {
            maps.forEach(m -> {
                map.put(MapUtil.getLong(m, "project_id"), MapUtil.getLong(m, "count"));
            });
        }
        return map;
    }

    @Override
    public Page<ProjectPeopleRelation> queryByProjectIdPage(Long projectId, Page<ProjectPeopleRelation> page) {
        LambdaQueryWrapper<ProjectPeopleRelation> queryWrapper = new LambdaQueryWrapper<ProjectPeopleRelation>()
                .eq(ProjectPeopleRelation::getProjectId, projectId)
                .eq(ProjectPeopleRelation::getYn, YnEnum.YES.getCode())
                .orderByAsc(ProjectPeopleRelation::getCreateAt);
        return this.page(page, queryWrapper);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(Long projectId, String userName) {
        List<ProjectPeopleRelation> list = this.list(new LambdaQueryWrapper<ProjectPeopleRelation>().eq(ProjectPeopleRelation::getPeopleId, projectId).eq(ProjectPeopleRelation::getYn, YnEnum.YES.getCode()));
        if (CollectionUtil.isNotEmpty(list)) {
            // 删除历史属性
            this.lambdaUpdate().eq(ProjectPeopleRelation::getProjectId, projectId)
                    .eq(ProjectPeopleRelation::getYn, YnEnum.YES.getCode())
                    .set(ProjectPeopleRelation::getModifyAt, Calendar.getInstance().getTime())
                    .set(ProjectPeopleRelation::getModifyName, userName)
                    .set(ProjectPeopleRelation::getYn, YnEnum.NO.getCode()).update();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean batchDeletePeople(Long projectId, List<Long> peopleIds, String userName) {
        boolean update = this.lambdaUpdate().eq(ProjectPeopleRelation::getProjectId, projectId)
                .in(ProjectPeopleRelation::getPeopleId, peopleIds)
                .eq(ProjectPeopleRelation::getYn, YnEnum.YES.getCode())
                .set(ProjectPeopleRelation::getModifyAt, Calendar.getInstance().getTime())
                .set(ProjectPeopleRelation::getModifyName, userName)
                .set(ProjectPeopleRelation::getYn, YnEnum.NO.getCode()).update();
        if (!update) {
            throw new APIException(AppCode.APP_ERROR, "批量删除参与人员失败！");
        }
        return update;
    }

    @Override
    public List<ProjectPeopleRelation> queryByProjectId(Long projectId) {
        return this.list(new LambdaQueryWrapper<ProjectPeopleRelation>().eq(ProjectPeopleRelation::getProjectId, projectId)
                .eq(ProjectPeopleRelation::getYn, YnEnum.YES.getCode()));
    }

    @Override
    public Boolean batchAddPeople(Long id, List<Long> peopleIds, String userName) {
        // 添加关系
        if (CollectionUtil.isNotEmpty(peopleIds)) {
            List<ProjectPeopleRelation> relations = peopleIds.stream().map(peopleId ->
                    ProjectPeopleRelation.builder().projectId(id).peopleId(peopleId)
                            .createAt(Calendar.getInstance().getTime()).createName(userName)
                            .modifyAt(Calendar.getInstance().getTime()).modifyName(userName)
                            .yn(YnEnum.YES.getCode()).build()).collect(Collectors.toList());
           return this.saveBatch(relations);
        }
        return false;
    }
}
