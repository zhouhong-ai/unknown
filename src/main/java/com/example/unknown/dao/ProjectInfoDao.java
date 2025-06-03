package com.example.unknown.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.unknown.domain.ProjectInfo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.Map;

@Mapper
public interface ProjectInfoDao extends BaseMapper<ProjectInfo> {

    @Select("SELECT status, COUNT(1) FROM tb_project_info WHERE yn = 1 GROUP BY status")
    Map<Integer, Long> statusClassStatic();
}
