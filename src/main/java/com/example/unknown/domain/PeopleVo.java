package com.example.unknown.domain;

import cn.hutool.core.collection.CollectionUtil;
import com.example.unknown.enums.YnEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PeopleVo {
    /**
     * 主键ID，自增
     */
    private Long id;
    /**
     * 主键ID集合
     */
    private List<Long> ids;
    /**
     * 过滤主键ID集合
     */
    private List<Long> filterIds;
    /**
     * 昵称
     */
    private String nickname;
    /**
     * 生日
     */
    private String birthday;
    /**
     * 体重
     */
    private PeoplePropertyVo weight;
    /**
     * 高度
     */
    private PeoplePropertyVo height;
    /**
     * 图片
     */
    private PeoplePropertyVo image;
    /**
     * 是否显示
     */
    private Integer show;
    /**
     * 用户属性ID
     */
    private Long propertyId;
    /**
     * 用户属性集合
     */
    private List<PeoplePropertyVo> propertyVos;

    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class PeoplePropertyVo {
        /**
         * 用户属性ID
         */
        private Long id;
        /**
         * 用户ID
         */
        private Long peopleId;
        /**
         * 属性名称
         */
        private String name;
        /**
         * 属性值
         */
        private String value;
        /**
         * 是否展示
         */
        private Integer show;
    }

    /**
     * buildPeopleInfo
     */
    public PeopleInfo buildPeopleInfo(String userName) {
        PeopleInfo peopleInfo = new PeopleInfo();
        BeanUtils.copyProperties(this, peopleInfo);
        peopleInfo.setYn(YnEnum.YES.getCode());
        peopleInfo.setCreateAt(Calendar.getInstance().getTime());
        peopleInfo.setCreateName(userName);
        peopleInfo.setModifyAt(Calendar.getInstance().getTime());
        peopleInfo.setModifyName(userName);
        // 属性
        List<PeopleProperty> properties = new ArrayList<>();
        if (Objects.nonNull(weight)) {
            PeopleProperty property = new PeopleProperty();
            BeanUtils.copyProperties(weight, property);
            properties.add(property);
        }
        if (Objects.nonNull(height)) {
            PeopleProperty property = new PeopleProperty();
            BeanUtils.copyProperties(height, property);
            properties.add(property);
        }
        if (Objects.nonNull(image)) {
            PeopleProperty property = new PeopleProperty();
            BeanUtils.copyProperties(image, property);
            properties.add(property);
        }
        if (CollectionUtil.isNotEmpty(propertyVos)) {
             properties.addAll(propertyVos.stream().map(p -> {
                 PeopleProperty property = new PeopleProperty();
                 BeanUtils.copyProperties(p, property);
                 return property;
             }).collect(Collectors.toList()));
        }
        if (CollectionUtil.isNotEmpty(properties)) {
            peopleInfo.setProperties(properties);
        }
        return peopleInfo;
    }
}
