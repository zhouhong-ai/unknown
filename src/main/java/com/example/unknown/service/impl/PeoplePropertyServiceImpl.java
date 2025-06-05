package com.example.unknown.service.impl;

import cn.hutool.core.collection.CollectionUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.example.unknown.dao.PeoplePropertyDao;
import com.example.unknown.domain.PeopleProperty;
import com.example.unknown.domain.PeopleVo;
import com.example.unknown.enums.AppCode;
import com.example.unknown.enums.YnEnum;
import com.example.unknown.exception.APIException;
import com.example.unknown.service.PeoplePropertyService;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service(value = "peoplePropertyService")
public class PeoplePropertyServiceImpl extends ServiceImpl<PeoplePropertyDao, PeopleProperty> implements PeoplePropertyService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchAdd(Long peopleId, List<PeopleProperty> properties, String userName) {
        this.batchDelete(peopleId, userName);
        // 批量创建属性
        List<PeopleProperty> peopleProperties = properties.stream().peek(p -> {
            p.setPeopleId(peopleId);
            p.setYn(YnEnum.YES.getCode());
            p.setCreateAt(Calendar.getInstance().getTime());
            p.setCreateName(userName);
            p.setModifyAt(Calendar.getInstance().getTime());
            p.setModifyName(userName);
        }).collect(Collectors.toList());
        if (!this.saveBatch(peopleProperties)) {
            throw new APIException(AppCode.APP_ERROR, "批量创建用户属性失败！");
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchDelete(Long peopleId, String userName) {
        List<PeopleProperty> list = this.list(new LambdaQueryWrapper<PeopleProperty>().eq(PeopleProperty::getPeopleId, peopleId).eq(PeopleProperty::getYn, YnEnum.YES.getCode()));
        if (CollectionUtil.isNotEmpty(list)) {
            // 删除历史属性
            this.lambdaUpdate().eq(PeopleProperty::getPeopleId, peopleId)
                    .eq(PeopleProperty::getYn, YnEnum.YES.getCode())
                    .set(PeopleProperty::getModifyAt, Calendar.getInstance().getTime())
                    .set(PeopleProperty::getModifyName, userName)
                    .set(PeopleProperty::getYn, YnEnum.NO.getCode()).update();
        }
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updatePropertyShow(Long id, Integer show, String userName) {
        this.checkPropertyExist(id);
        // 修改用户属性是否展示
        PeopleProperty updatePeopleProperty = PeopleProperty.builder().id(id).show(show)
                .modifyAt(Calendar.getInstance().getTime()).modifyName(userName).build();
        if (!this.updateById(updatePeopleProperty)) {
            throw new APIException(AppCode.APP_ERROR, "修改用户属性是否展示失败！");
        }
        return true;
    }

    @Override
    public Map<Long, List<PeopleVo.PeoplePropertyVo>> queryPropertyByPeopleIds(List<Long> peopleIds) {
        LambdaQueryWrapper<PeopleProperty> queryWrapper = new LambdaQueryWrapper<PeopleProperty>()
                .in(PeopleProperty::getPeopleId, peopleIds)
                .eq(PeopleProperty::getYn, YnEnum.YES.getCode());
        List<PeopleProperty> properties = this.list(queryWrapper);
        if (CollectionUtil.isNotEmpty(properties)) {
            return properties.stream().map(p -> {
                PeopleVo.PeoplePropertyVo propertyVo = new PeopleVo.PeoplePropertyVo();
                BeanUtils.copyProperties(p, propertyVo);
                return propertyVo;
            }).collect(Collectors.groupingBy(PeopleVo.PeoplePropertyVo::getPeopleId));
        }
        return Collections.emptyMap();
    }

    /**
     * 判断用户属性是否存在
     */
    private void checkPropertyExist(Long id) {
        PeopleProperty property = this.getById(id);
        if (Objects.isNull(property)) {
            throw new APIException(AppCode.APP_ERROR, "用户属性不存在！");
        }
    }
}
