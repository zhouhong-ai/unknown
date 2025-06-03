package com.example.unknown.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.example.unknown.domain.PeopleProperty;
import com.example.unknown.domain.PeopleVo;

import java.util.List;
import java.util.Map;

public interface PeoplePropertyService extends IService<PeopleProperty> {
    /**
     * 批量创建用户属性
     */
    void batchAdd(Long peopleId, List<PeopleProperty> properties, String userName);
    /**
     * 批量删除用户属性
     */
    void batchDelete(Long peopleId, String userName);
    /**
     * 修改用户属性是否展示
     */
    Boolean updatePropertyShow(Long id, Integer show, String userName);
    /**
     * 查询用户对用属性Map
     */
    Map<Long, List<PeopleVo.PeoplePropertyVo>> queryPropertyByPeopleIds(List<Long> peopleIds);
}
