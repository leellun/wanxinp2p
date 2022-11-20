package com.newland.wanxin.depository.service;

import com.newland.wanxin.depository.entity.Tender;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.wanxin.depository.model.request.UserAutoPreTransactionRequest;
import com.newland.wanxin.depository.model.response.ConfirmLoanResponse;
import com.newland.wanxin.depository.model.response.UserAutoPreTransactionResponse;

/**
 * <p>
 * 投标信息表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
public interface TenderService extends IService<Tender> {
    /**
     * 投标预授权处理
     * @param preTransactionRequest
     * @return
     */
    UserAutoPreTransactionResponse autoPreTransactionForTender(UserAutoPreTransactionRequest preTransactionRequest);

    /**
     * 放款确认
     * @param reqData
     * @return
     */
    ConfirmLoanResponse confirmLoan(String reqData);
}
