package com.example.unknown.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.unknown.domain.LoginUer;
import com.example.unknown.domain.LoginUerVo;

import java.util.List;
import java.util.Map;

public interface LoginUerService extends IService<LoginUer> {
    /**
     * 登陆用户创建
     */
    Boolean create(LoginUer loginUer);
    /**
     * 分页查询
     */
    Page<LoginUerVo> queryPage(LoginUerVo vo, Page<LoginUer> page);
    /**
     * 查询项目对应登陆人员数量
     */
    Map<Long, Long> queryLoginUserNumByProjectIds(List<Long> projectIds);
    /**
     * 更新登陆用户最后访问项目ID
     */
    Boolean updateLastBrowseProjectId(Long id, Long projectId);
}
