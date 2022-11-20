package com.newland.wanxin.search.service;

import com.newland.wanxin.api.search.model.ProjectQueryParamsDTO;
import com.newland.wanxin.api.transaction.model.ProjectDTO;
import com.newland.wanxin.domain.PageVO;

/**
 * 标的检索业务层接口
 */
public interface ProjectIndexService {
    PageVO<ProjectDTO> queryProjectIndex(ProjectQueryParamsDTO
                                                 projectQueryParamsDTO,
                                         Integer pageNo, Integer pageSize,
                                         String sortBy, String order);
}
