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

/**
 * 后台用户实体类
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("tb_backend_user")
public class BackendUser {
    /**
     * 主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 账户
     */
    @TableField(value = "account")
    private String account;
    /**
     * 密码
     */
    @TableField(value = "password")
    private String password;
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
