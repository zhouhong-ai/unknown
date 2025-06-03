package com.example.unknown.domain;

import com.example.unknown.enums.ProjectStatusEnum;
import com.example.unknown.enums.YnEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Calendar;
import java.util.Date;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProjectVO {
    /**
     * 项目ID
     */
    private Long id;
    /**
     * 项目名称
     */
    private String name;
    /**
     * 项目图片地址
     */
    private String imageUrl;
    /**
     * 描述
     */
    private String desc;
    /**
     * 状态: 1(未开始)、2(招募中)、3(已结束)
     */
    private Integer status;
    /**
     * 项目周期,开始时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date startTime;
    /**
     * 项目周期,结束时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date endTime;
    /**
     * 创建时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date createAt;
    /**
     * 创建用户名称
     */
    private String createName;
    /**
     * 参与人员IDs
     */
    private List<Long> peopleIds;
    /**
     * 参与人员数量
     */
    private long peopleNum;
    /**
     * 登陆人员数量
     */
    private long loginUserNum;

    /**
     * buildProjectInfo
     */
    public ProjectInfo buildProjectInfo(String userName) {
        ProjectInfo projectInfo = new ProjectInfo();
        BeanUtils.copyProperties(this, projectInfo);
        projectInfo.setStatus(ProjectStatusEnum.NO_START.getCode());
        projectInfo.setYn(YnEnum.YES.getCode());
        projectInfo.setCreateAt(Calendar.getInstance().getTime());
        projectInfo.setCreateName(userName);
        projectInfo.setModifyAt(Calendar.getInstance().getTime());
        projectInfo.setModifyName(userName);
        return projectInfo;
    }
}
