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
import java.util.List;

/**
 * 项目实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_project_info")
public class ProjectInfo {
    /**
     * 主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 项目名称
     */
    @TableField(value = "name")
    private String name;
    /**
     * 项目图片地址
     */
    @TableField(value = "image_url")
    private String imageUrl;
    /**
     * 描述
     */
    @TableField(value = "`desc`")
    private String desc;
    /**
     * 状态: 1(未开始)、2(招募中)、3(已结束)
     */
    @TableField(value = "`status`")
    private Integer status;
    /**
     * 项目周期,开始时间
     */
    @TableField(value = "start_time")
    private Date startTime;
    /**
     * 项目周期,结束时间
     */
    @TableField(value = "end_time")
    private Date endTime;
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

    /**
     * 参与人员IDs
     */
    @TableField(exist = false)
    private List<Long> peopleIds;
}
