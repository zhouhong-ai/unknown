package com.example.unknown.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.example.unknown.domain.ProjectInfo;
import com.example.unknown.domain.ProjectVO;

import java.util.Map;

public interface ProjectInfoService extends IService<ProjectInfo> {
    /**
     * 项目创建
     */
    Boolean createOrUpdate(ProjectInfo projectInfo);
    /**
     * 根据ID获取详情
     */
    ProjectVO queryById(Long id);
    /**
     * 分页查询
     */
    Page<ProjectVO> queryPage(ProjectVO vo, Page<ProjectInfo> page);
    /**
     * 项目状态修改
     */
    Boolean updateStatus(Long id, Integer status, String userName);
    /**
     * 项目删除
     */
    Boolean delete(Long id, String userName);
    /**
     * 项目状态分类统计数量
     */
    Map<String, Long> statusClassStatic();
}
