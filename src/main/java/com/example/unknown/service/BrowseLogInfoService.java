package com.example.unknown.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.unknown.domain.BrowseLogInfo;
import com.example.unknown.domain.LoginUerVo;

public interface BrowseLogInfoService extends IService<BrowseLogInfo> {
    /**
     * 添加用户浏览项目日志
     */
    void insertBrowseProjectLog(Long loginUserId, Long projectId);
    /**
     * 统计项目浏览次数
     */
    int projectBrowseCount(Long projectId);
    /**
     * 分页查询项目浏览用户信息
     */
    Page<LoginUerVo> queryPageByProjectId(Long projectId, Page<BrowseLogInfo> page);
}
