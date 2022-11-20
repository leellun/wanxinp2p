package com.newland.wanxin.depository.service;

import com.newland.wanxin.depository.entity.RechargeDetails;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.wanxin.depository.model.request.RechargeRequest;
import com.newland.wanxin.depository.model.response.RechargeResponse;

/**
 * <p>
 * 充值记录表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
public interface RechargeDetailsService extends IService<RechargeDetails> {
    /**
     * 用户充值
     * @param rechargeRequest
     * @return
     */
    RechargeResponse recharge(RechargeRequest rechargeRequest);
}
