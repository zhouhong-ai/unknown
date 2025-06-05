package com.example.unknown.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.map.MapUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.unknown.dao.LoginUerDao;
import com.example.unknown.domain.LoginUer;
import com.example.unknown.domain.LoginUerVo;
import com.example.unknown.enums.AppCode;
import com.example.unknown.enums.YnEnum;
import com.example.unknown.exception.APIException;
import com.example.unknown.service.LoginUerService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service(value = "loginUerService")
public class LoginUerServiceImpl extends ServiceImpl<LoginUerDao, LoginUer> implements LoginUerService {

    @Autowired
    private LoginUerDao loginUerDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean create(LoginUer loginUer) {
        loginUer.setYn(YnEnum.YES.getCode());
        if (!this.save(loginUer)) {
            throw new APIException(AppCode.APP_ERROR, "登陆用户创建失败！");
        }
        return true;
    }

    @Override
    public Page<LoginUerVo> queryPage(LoginUerVo vo, Page<LoginUer> page) {
        Page<LoginUerVo> voPage = new Page<>();

        LambdaQueryWrapper<LoginUer> queryWrapper = new LambdaQueryWrapper<LoginUer>()
                .eq(StrUtil.isNotBlank(vo.getPhone()), LoginUer::getPhone, vo.getPhone())
                .eq(StrUtil.isNotBlank(vo.getNickname()), LoginUer::getNickname, vo.getNickname())
                .in(CollectionUtil.isNotEmpty(vo.getIds()), LoginUer::getId, vo.getIds())
                .eq(LoginUer::getYn, YnEnum.YES.getCode())
                .orderByDesc(LoginUer::getLastLoginTime);
        Page<LoginUer> resultPage = this.page(page, queryWrapper);
        if (CollectionUtil.isNotEmpty(resultPage.getRecords())) {
            BeanUtils.copyProperties(resultPage, voPage);
            voPage.setRecords(resultPage.getRecords().stream().map(l -> {
                LoginUerVo loginUerVo = new LoginUerVo();
                BeanUtils.copyProperties(l, loginUerVo);
                return loginUerVo;
            }).collect(Collectors.toList()));
        }
        return voPage;
    }

    @Override
    public Map<Long, Long> queryLoginUserNumByProjectIds(List<Long> projectIds) {
        Map<Long, Long> map = new HashMap<>();

        QueryWrapper<LoginUer> queryWrapper = new QueryWrapper<>();
        queryWrapper.groupBy("last_browse_project_id");
        queryWrapper.select("last_browse_project_id", "count(1) AS count");
        List<Map<String, Object>> maps = loginUerDao.selectMaps(queryWrapper);
        if (CollectionUtil.isNotEmpty(maps)) {
            maps.forEach(m -> {
                map.put(MapUtil.getLong(m, "last_browse_project_id"), MapUtil.getLong(m, "count"));
            });
        }
        return map;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateLastBrowseProjectId(Long id, Long projectId) {
        return this.updateById(LoginUer.builder().id(id).lastBrowseProjectId(projectId).build());
    }
}
