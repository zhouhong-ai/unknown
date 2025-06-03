package com.example.unknown.domain;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_people_property")
public class PeopleProperty {
    /**
     * 主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 用户ID
     */
    @TableField(value = "people_id")
    private Long peopleId;
    /**
     * 属性名称
     */
    @TableField(value = "name")
    private String name;
    /**
     * 属性值
     */
    @TableField(value = "`value`")
    private String value;
    /**
     * 是否显示: 0(不显示)、1(显示)
     */
    @TableField(value = "`show`")
    private Integer show;
    /**
     * 创建时间
     */
    @TableField(value = "create_at")
    private Date createAt;
    /**
     * 创建用户名称
     */
    @TableField(value = "create_name")
    private String createName;
    /**
     * 修改时间
     */
    @TableField(value = "modify_at")
    private Date modifyAt;
    /**
     * 修改用户名称
     */
    @TableField(value = "modify_name")
    private String modifyName;
    /**
     * 是否删除: 0(删除)、1(不删除)
     */
    @TableField(value = "yn")
    private Integer yn;
}
