package com.newland.wanxin.depository.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.newland.wanxin.depository.common.constant.ProjectStatusCode;
import com.newland.wanxin.depository.common.util.EncryptUtil;
import com.newland.wanxin.depository.entity.Project;
import com.newland.wanxin.depository.mapper.ProjectMapper;
import com.newland.wanxin.depository.model.request.CreateProjectRequest;
import com.newland.wanxin.depository.model.request.ModifyProjectRequest;
import com.newland.wanxin.depository.model.response.CreateProjectResponse;
import com.newland.wanxin.depository.model.response.ModifyProjectResponse;
import com.newland.wanxin.depository.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 标的信息表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Override
    public CreateProjectResponse createProject(String reqData) {
        String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
        CreateProjectRequest request = JSON.parseObject(decodeReqData, CreateProjectRequest.class);
        String requestNo = request.getRequestNo();

        //保存标的信息
        Project project = new Project();
        BeanUtils.copyProperties(request, project);
        project.setName(request.getProjectName());
        project.setDescription(request.getProjectDesc());
        project.setType(request.getProjectType());
        project.setPeriod(request.getProjectPeriod());
        project.setBorrowerAnnualRate(request.getAnnualRate());
        project.setAmount(request.getProjectAmount());
        project.setProjectStatus(ProjectStatusCode.RAISE.getCode());
        save(project);

        return new CreateProjectResponse(requestNo);
    }

    @Override
    public ModifyProjectResponse modifyProject(String reqData) {
        String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
        ModifyProjectRequest request = JSON.parseObject(decodeReqData, ModifyProjectRequest.class);
        String requestNo = request.getRequestNo();

        update(Wrappers.<Project>update().lambda().eq(Project::getProjectNo, request.getProjectNo())
                .set(Project::getProjectStatus, request.getProjectStatus()));

        return new ModifyProjectResponse(requestNo);
    }

    @Override
    public Project getByProjectNo(String projectNo) {
        return getOne(Wrappers.<Project>query().lambda().eq(Project::getProjectNo, projectNo), false);
    }
}
