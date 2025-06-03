package com.example.unknown.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.unknown.dao.ProjectPeopleRelationDao;
import com.example.unknown.domain.ProjectPeopleRelation;
import com.example.unknown.enums.YnEnum;
import com.example.unknown.service.ProjectPeopleRelationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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
            LambdaUpdateChainWrapper<ProjectPeopleRelation> wrapper = this.lambdaUpdate().eq(ProjectPeopleRelation::getProjectId, projectId)
                    .set(ProjectPeopleRelation::getModifyAt, Calendar.getInstance().getTime())
                    .set(ProjectPeopleRelation::getModifyName, userName)
                    .set(ProjectPeopleRelation::getYn, YnEnum.NO.getCode());
            this.update(wrapper);
        }
        // 添加关系
        if (CollectionUtil.isNotEmpty(peopleIds)) {
            List<ProjectPeopleRelation> relations = peopleIds.stream().map(id ->
                    ProjectPeopleRelation.builder().projectId(projectId).peopleId(id)
                            .createAt(Calendar.getInstance().getTime()).createName(userName)
                            .modifyAt(Calendar.getInstance().getTime()).modifyName(userName)
                            .yn(YnEnum.YES.getCode()).build()).collect(Collectors.toList());
            this.saveBatch(relations);
        }
    }

    @Override
    public Map<Long, Long> queryPeopleNumByProjectIds(List<Long> projectIds) {
        Map<Long, Long> map = new HashMap<>();

        QueryWrapper<ProjectPeopleRelation> queryWrapper = new QueryWrapper<>();
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
}
