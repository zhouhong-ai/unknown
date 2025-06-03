package com.example.unknown.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.unknown.dao.PeopleInfoDao;
import com.example.unknown.domain.PeopleInfo;
import com.example.unknown.domain.PeopleVo;
import com.example.unknown.domain.ProjectPeopleRelation;
import com.example.unknown.enums.AppCode;
import com.example.unknown.enums.YnEnum;
import com.example.unknown.exception.APIException;
import com.example.unknown.service.PeopleInfoService;
import com.example.unknown.service.PeoplePropertyService;
import com.example.unknown.service.ProjectPeopleRelationService;
import com.example.unknown.utils.ContextUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Service(value = "peopleInfoService")
public class PeopleInfoServiceImpl extends ServiceImpl<PeopleInfoDao, PeopleInfo> implements PeopleInfoService {

    @Autowired
    private PeoplePropertyService peoplePropertyService;

    @Autowired
    private ProjectPeopleRelationService projectPeopleRelationService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean createOrUpdate(PeopleInfo vo) {
        if (Objects.isNull(vo.getId())) {
            this.checkPeopleNicknameExist(vo.getNickname());
            // 创建用户
            if (!this.save(vo)) {
                throw new APIException(AppCode.APP_ERROR, "用户创建失败！");
            }
        } else {
            PeopleInfo peopleInfo = this.getById(vo.getId());
            if (Objects.isNull(peopleInfo)) {
                throw new APIException(AppCode.APP_ERROR, "用户不存在！");
            }
            if (!peopleInfo.getNickname().equals(vo.getNickname())) {
                this.checkPeopleNicknameExist(vo.getNickname());
            }
            // 修改用户
            PeopleInfo updatePeopleInfo = PeopleInfo.builder().id(vo.getId())
                    .nickname(vo.getNickname()).birthday(vo.getBirthday())
                    .modifyAt(vo.getModifyAt()).modifyName(vo.getModifyName()).build();
            if (!this.updateById(updatePeopleInfo)) {
                throw new APIException(AppCode.APP_ERROR, "用户修改失败！");
            }
        }
        // 绑定用户属性
        peoplePropertyService.batchAdd(vo.getId(), vo.getProperties(), ContextUtil.getUserName());
        return true;
    }

    @Override
    public Page<PeopleVo> queryPage(PeopleVo vo, Page<PeopleInfo> page) {
        Page<PeopleVo> voPage = new Page<>();

        LambdaQueryWrapper<PeopleInfo> queryWrapper = new LambdaQueryWrapper<PeopleInfo>()
                .eq(Objects.nonNull(vo.getId()), PeopleInfo::getId, vo.getId())
                .in(CollectionUtil.isNotEmpty(vo.getIds()), PeopleInfo::getId, vo.getIds())
                .eq(StrUtil.isNotBlank(vo.getNickname()), PeopleInfo::getModifyName, vo.getNickname())
                .eq(PeopleInfo::getYn, YnEnum.YES.getCode())
                .orderByDesc(PeopleInfo::getCreateAt);
        IPage<PeopleInfo> resultPage = this.page(page, queryWrapper);
        if (CollectionUtil.isNotEmpty(resultPage.getRecords())) {
            // 查询用户属性
            List<Long> peopleIds = resultPage.getRecords().stream().map(PeopleInfo::getId).collect(Collectors.toList());
            Map<Long, List<PeopleVo.PeoplePropertyVo>> propertyMap = peoplePropertyService.queryPropertyByPeopleIds(peopleIds);

            BeanUtils.copyProperties(resultPage, voPage);
            voPage.setRecords(resultPage.getRecords().stream().map(p -> {
                PeopleVo peopleVo = new PeopleVo();
                BeanUtils.copyProperties(p, peopleVo);
                peopleVo.setPropertyVos(propertyMap.get(p.getId()));
                return peopleVo;
            }).collect(Collectors.toList()));
        }
        return voPage;
    }

    @Override
    public Boolean delete(Long id, String userName) {
        this.checkPeopleExist(id);
        // 删除用户
        PeopleInfo updatePeopleInfo = PeopleInfo.builder().id(id)
                .modifyAt(Calendar.getInstance().getTime()).modifyName(userName).build();
        if (!this.updateById(updatePeopleInfo)) {
            throw new APIException(AppCode.APP_ERROR, "用户删除失败！");
        }
        // 删除用户属性

        return true;
    }

    @Override
    public Page<PeopleVo> queryProjectPeoplePage(Long projectId, Page<ProjectPeopleRelation> page) {
        Page<PeopleVo> voPage = new Page<>();

        Page<ProjectPeopleRelation> relationPage = projectPeopleRelationService.queryByProjectIdPage(projectId, page);
        if (CollectionUtil.isNotEmpty(relationPage.getRecords())) {
            PeopleVo vo = new PeopleVo();
            vo.setIds(relationPage.getRecords().stream().map(ProjectPeopleRelation::getPeopleId).collect(Collectors.toList()));
            Page<PeopleInfo> pPage = new Page<>();
            BeanUtils.copyProperties(page, pPage);
            return this.queryPage(vo, pPage);
        }
        return voPage;
    }

    /**
     * 判断是否存在
     */
    private void checkPeopleExist(Long id) {
        PeopleInfo peopleInfo = this.getById(id);
        if (Objects.nonNull(peopleInfo)) {
            throw new APIException(AppCode.APP_ERROR, "用户不存在！");
        }
    }

    /**
     * 判断用户是否存在
     */
    private void checkPeopleNicknameExist(String nickname) {
        PeopleInfo peopleInfo = this.getOne(new LambdaQueryWrapper<PeopleInfo>().eq(PeopleInfo::getNickname, nickname).eq(PeopleInfo::getYn, YnEnum.YES.getCode()));
        if (Objects.nonNull(peopleInfo)) {
            throw new APIException(AppCode.APP_ERROR, String.format("%s用户已经存在！", nickname));
        }
    }
}
