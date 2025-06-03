package com.example.unknown.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.unknown.dao.ProjectInfoDao;
import com.example.unknown.domain.ProjectInfo;
import com.example.unknown.domain.ProjectVO;
import com.example.unknown.enums.AppCode;
import com.example.unknown.enums.YnEnum;
import com.example.unknown.exception.APIException;
import com.example.unknown.service.LoginUerService;
import com.example.unknown.service.ProjectInfoService;
import com.example.unknown.service.ProjectPeopleRelationService;
import com.example.unknown.utils.ContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 项目Service类
 */
@Service(value = "projectInfoService")
public class ProjectInfoServiceImpl extends ServiceImpl<ProjectInfoDao, ProjectInfo> implements ProjectInfoService {

    @Autowired
    private ProjectPeopleRelationService projectPeopleRelationService;

    @Autowired
    private LoginUerService loginUerService;

    @Autowired
    private ProjectInfoDao projectInfoDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createOrUpdate(ProjectInfo vo) {
        if (Objects.isNull(vo.getId())) {
            this.checkProjectNameExist(vo.getName());
            // 添加项目
            if (!this.save(vo)) {
                throw new APIException(AppCode.APP_ERROR, "项目添加失败！");
            }
        } else {
            ProjectInfo projectInfo = this.getById(vo.getId());
            if (Objects.isNull(projectInfo)) {
                throw new APIException(AppCode.APP_ERROR, "项目不存在");
            }
            if (!projectInfo.getName().equals(vo.getName())) {
             this.checkProjectNameExist(vo.getName());
            }
            // 修改项目
            ProjectInfo updateProjectInfo = ProjectInfo.builder().id(vo.getId()).name(vo.getName())
                    .imageUrl(vo.getImageUrl()).desc(vo.getDesc()).startTime(vo.getStartTime()).endTime(vo.getEndTime())
                    .modifyAt(vo.getModifyAt()).modifyName(vo.getModifyName()).build();
            if (!this.updateById(updateProjectInfo)) {
                throw new APIException(AppCode.APP_ERROR, "项目修改失败！");
            }
        }
        // 绑定参加用户
        projectPeopleRelationService.batchAdd(vo.getId(), vo.getPeopleIds(), ContextUtil.getUserName());
        return true;
    }

    @Override
    public Page<ProjectVO> queryPage(ProjectVO vo, Page<ProjectInfo> page) {
        Page<ProjectVO> voPage = new Page<>();

        LambdaQueryWrapper<ProjectInfo> queryWrapper = new LambdaQueryWrapper<ProjectInfo>()
                .eq(StrUtil.isNotBlank(vo.getName()), ProjectInfo::getName, vo.getName())
                .eq(ProjectInfo::getYn, YnEnum.YES.getCode())
                .orderByDesc(ProjectInfo::getCreateAt);
        Page<ProjectInfo> resultPage = this.page(page, queryWrapper);
        if (CollectionUtil.isNotEmpty(resultPage.getRecords())) {
            List<Long> projectIds = resultPage.getRecords().stream().map(ProjectInfo::getId).collect(Collectors.toList());
            // 查询项目对应参加人数
            Map<Long, Long> projectMap = projectPeopleRelationService.queryPeopleNumByProjectIds(projectIds);
            // 查询项目对应浏览人数
            Map<Long, Long> loginUserMap = loginUerService.queryLoginUserNumByProjectIds(projectIds);

            BeanUtils.copyProperties(resultPage, voPage);
            voPage.setRecords(resultPage.getRecords().stream().map(p -> {
                ProjectVO projectVO = new ProjectVO();
                BeanUtils.copyProperties(p, projectVO);
                Long peopleNum = projectMap.get(p.getId());
                projectVO.setPeopleNum(Objects.nonNull(peopleNum) ? peopleNum : 0);
                Long loginUserNum = loginUserMap.get(p.getId());
                projectVO.setLoginUserNum(Objects.nonNull(loginUserNum) ? loginUserNum : 0);
                return projectVO;
            }).collect(Collectors.toList()));
        }
        return voPage;
    }

    @Override
    public Boolean updateStatus(Long id, Integer status, String userName) {
        this.checkProjectExist(id);
        // 修改项目状态
        ProjectInfo updateProjectInfo = ProjectInfo.builder().id(id).status(status)
                .modifyAt(Calendar.getInstance().getTime()).modifyName(userName).build();
        if (!this.updateById(updateProjectInfo)) {
            throw new APIException(AppCode.APP_ERROR, "项目状态修改失败！");
        }
        return true;
    }

    @Override
    public Boolean delete(Long id, String userName) {
        this.checkProjectExist(id);
        // 项目删除
        ProjectInfo updateProjectInfo = ProjectInfo.builder().id(id).yn(YnEnum.NO.getCode())
                .modifyAt(Calendar.getInstance().getTime()).modifyName(userName).build();
        if (!this.updateById(updateProjectInfo)) {
            throw new APIException(AppCode.APP_ERROR, "项目删除失败！");
        }
        return true;
    }

    @Override
    public Map<Integer, Long> statusClassStatic() {
        return projectInfoDao.statusClassStatic();
    }

    /**
     * 判断项目是否存在
     */
    private void checkProjectExist(Long id) {
        ProjectInfo projectInfo = this.getById(id);
        if (Objects.isNull(projectInfo)) {
            throw new APIException(AppCode.APP_ERROR, "项目不存在");
        }
    }

    /**
     * 判断项目名称是否存在
     */
    private void checkProjectNameExist(String name) {
        ProjectInfo projectInfo = this.getOne(new LambdaQueryWrapper<ProjectInfo>().eq(ProjectInfo::getName, name).eq(ProjectInfo::getYn, YnEnum.YES.getCode()));
        if (Objects.nonNull(projectInfo)) {
            throw new APIException(AppCode.APP_ERROR, String.format("%s项目已经存在！", name));
        }
    }
}
