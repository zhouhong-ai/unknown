package com.example.unknown.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.unknown.domain.ProjectPeopleRelation;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface ProjectPeopleRelationDao extends BaseMapper<ProjectPeopleRelation> {
    /**
     * 查询项目对应参与人数
     */
    Map<Long, Long> queryPeopleByProjectIds(@Param("projectIds") List<Long> projectIds);
}
