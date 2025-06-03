package com.example.unknown.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.unknown.domain.LoginUer;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface LoginUerDao extends BaseMapper<LoginUer> {
    /**
     * 查询项目对应登陆人员数量
     */
    Map<Long, Long> queryLoginUserNumByProjectIds(List<Long> projectIds);
}
