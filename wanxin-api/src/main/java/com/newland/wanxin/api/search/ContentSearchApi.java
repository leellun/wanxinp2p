package com.newland.wanxin.api.search;

import com.newland.wanxin.api.search.model.ProjectQueryParamsDTO;
import com.newland.wanxin.api.transaction.model.ProjectDTO;
import com.newland.wanxin.domain.PageVO;
import com.newland.wanxin.domain.RestResponse;

public interface ContentSearchApi {
    /**
     * 检索标的
     * @param projectQueryParamsDTO
     * @return
     */
    RestResponse<PageVO<ProjectDTO>> queryProjectIndex(
            ProjectQueryParamsDTO projectQueryParamsDTO,
            Integer pageNo,Integer pageSize,String sortBy,String order);
}
