package com.newland.wanxin.api.repayment;

import com.newland.wanxin.api.repayment.model.ProjectWithTendersDTO;
import com.newland.wanxin.domain.RestResponse;

public interface RepaymentApi {

    /**
     * 启动还款
     * @param projectWithTendersDTO
     * @return
     */
    public RestResponse<String> startRepayment(ProjectWithTendersDTO
                                                       projectWithTendersDTO);
}
