package com.example.unknown.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.example.unknown.domain.BackendUser;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface BackendUserDao extends BaseMapper<BackendUser> {
}
