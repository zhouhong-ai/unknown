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
@TableName("tb_login_user_info")
public class LoginUer {
    /**
     * 主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 手机号
     */
    @TableField(value = "phone")
    private String phone;
    /**
     * 用户昵称
     */
    @TableField(value = "nickname")
    private String nickname;
    /**
     * 最后登陆时间
     */
    @TableField(value = "last_login_time")
    private Date lastLoginTime;
    /**
     * 最后访问项目ID
     */
    @TableField(value = "last_browse_project_id")
    private Long lastBrowseProjectId;
    /**
     * 是否删除: 0(删除)、1(不删除)
     */
    @TableField(value = "yn")
    private Integer yn;
}
