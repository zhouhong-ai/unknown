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
@TableName("tb_browse_log_info")
public class BrowseLogInfo {
    /**
     * 主键ID，自增
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;
    /**
     * 登陆用户ID
     */
    @TableField(value = "login_user_id")
    private Long loginUserId;
    /**
     * 浏览内容
     */
    @TableField(value = "create_at")
    private String content;
    /**
     * 类型：1（项目）
     */
    @TableField(value = "create_at")
    private Integer type;
    /**
     * 创建时间
     */
    @TableField(value = "create_at")
    private Date createAt;
    /**
     * 是否删除: 0(删除)、1(不删除)
     */
    @TableField(value = "yn")
    private Integer yn;
}
