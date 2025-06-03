package com.example.unknown.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.unknown.dao.BrowseLogInfoDao;
import com.example.unknown.domain.BrowseLogInfo;
import com.example.unknown.domain.LoginUer;
import com.example.unknown.domain.LoginUerVo;
import com.example.unknown.domain.ProjectInfo;
import com.example.unknown.enums.BrowseTypeEnum;
import com.example.unknown.enums.YnEnum;
import com.example.unknown.service.BrowseLogInfoService;
import com.example.unknown.service.LoginUerService;
import com.example.unknown.service.ProjectInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Calendar;
import java.util.Objects;
import java.util.stream.Collectors;

@Slf4j
@Service(value = "browseLogInfoService")
public class BrowseLogInfoServiceImpl extends ServiceImpl<BrowseLogInfoDao, BrowseLogInfo> implements BrowseLogInfoService {

    private static final String PROJECT_CONTENT = "浏览项目日志：用户ID【%s】，用户名称【%s】，项目ID【%s】，项目名称【%s】";

    @Autowired
    private LoginUerService loginUerService;

    @Autowired
    private ProjectInfoService projectInfoService;

    @Override
    public void insertBrowseProjectLog(Long loginUserId, Long projectId) {
        // 查询项目信息
        ProjectInfo projectInfo = projectInfoService.getById(projectId);
        // 查询用户信息
        LoginUer loginUer = loginUerService.getById(loginUserId);
        // 组装content
        String content = String.format(PROJECT_CONTENT,
                loginUserId, Objects.nonNull(loginUer) ? loginUer.getNickname() : null,
                projectId, Objects.nonNull(projectInfo) ? projectInfo.getName() : null);
        // 1、添加用户浏览项目日志
        BrowseLogInfo browseLogInfo = BrowseLogInfo.builder().loginUserId(loginUserId)
                .content(content).type(BrowseTypeEnum.PROJECT.getCode())
                .createAt(Calendar.getInstance().getTime()).yn(YnEnum.YES.getCode()).build();
        boolean save = this.save(browseLogInfo);
        log.info("[添加项目浏览日志]结果：result={}, browseLogInfo={}", save, JSONObject.toJSONString(browseLogInfo));
        // 2、更新登陆用户最后访问项目
        Boolean b = loginUerService.updateLastBrowseProjectId(loginUserId, projectId);
        log.info("[添加项目浏览日志]更新登陆用户最后浏览项目ID：result={},", b);
    }

    @Override
    public int projectBrowseCount(Long projectId) {
        LambdaQueryWrapper<BrowseLogInfo> queryWrapper = new LambdaQueryWrapper<BrowseLogInfo>()
                .eq(BrowseLogInfo::getType, BrowseTypeEnum.PROJECT.getCode())
                .like(BrowseLogInfo::getContent, String.format("项目ID【%s】", projectId));
        return this.count(queryWrapper);
    }

    @Override
    public Page<LoginUerVo> queryPageByProjectId(Long projectId, Page<BrowseLogInfo> page) {
        Page<LoginUerVo> voPage = new Page<>();

        LambdaQueryWrapper<BrowseLogInfo> queryWrapper = new LambdaQueryWrapper<BrowseLogInfo>()
                .eq(BrowseLogInfo::getType, BrowseTypeEnum.PROJECT.getCode())
                .like(BrowseLogInfo::getContent, String.format("项目ID【%s】", projectId))
                .orderByDesc(BrowseLogInfo::getCreateAt)
                .groupBy(BrowseLogInfo::getLoginUserId);
        Page<BrowseLogInfo> resultPage = this.page(page, queryWrapper);
        if (CollectionUtil.isNotEmpty(resultPage.getRecords())) {
            Page<LoginUer> loginUerPage = new Page<>();
            BeanUtils.copyProperties(page, loginUerPage);
            LoginUerVo vo = new LoginUerVo();
            vo.setIds(resultPage.getRecords().stream().map(BrowseLogInfo::getLoginUserId).collect(Collectors.toList()));
            return loginUerService.queryPage(vo, loginUerPage);
        }
        return voPage;
    }
}
