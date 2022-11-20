package com.newland.wanxin.depository.service;

import com.newland.wanxin.depository.entity.WithdrawDetails;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.wanxin.depository.model.request.WithdrawRequest;
import com.newland.wanxin.depository.model.response.WithdrawResponse;

/**
 * <p>
 * 用户余额明细表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
public interface WithdrawDetailsService extends IService<WithdrawDetails> {
    /**
     * 用户提现
     * @param withdrawRequest
     * @return
     */
    WithdrawResponse withDraw(WithdrawRequest withdrawRequest);
}
