package com.newland.wanxin.depository.service;

import com.newland.wanxin.depository.entity.Project;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.wanxin.depository.model.response.CreateProjectResponse;
import com.newland.wanxin.depository.model.response.ModifyProjectResponse;

/**
 * <p>
 * 标的信息表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
public interface ProjectService extends IService<Project> {
    /**
     * 创建标的
     * @param reqData 业务数据
     * @return
     */
    CreateProjectResponse createProject(String reqData);

    /**
     * 更新标的状态
     * @param reqData 业务数据
     * @return
     */
    ModifyProjectResponse modifyProject(String reqData);

    /**
     * 根据标的编号获取标的信息
     * @param projectNo
     * @return
     */
    Project getByProjectNo(String projectNo);
}
