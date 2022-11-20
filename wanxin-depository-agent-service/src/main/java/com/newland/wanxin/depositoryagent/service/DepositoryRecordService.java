package com.newland.wanxin.depositoryagent.service;

import com.newland.wanxin.api.consumer.model.ConsumerRequest;
import com.newland.wanxin.api.depository.GatewayRequest;
import com.newland.wanxin.api.depository.model.*;
import com.newland.wanxin.api.transaction.model.ModifyProjectStatusDTO;
import com.newland.wanxin.api.transaction.model.ProjectDTO;
import com.newland.wanxin.depositoryagent.entity.DepositoryRecord;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 存管交易记录表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
public interface DepositoryRecordService extends IService<DepositoryRecord> {
    /**
     * 开通存管账户
     *
     * @param consumerRequest 开户信息
     * @return
     */
    GatewayRequest createConsumer(ConsumerRequest consumerRequest);

    /**
     * 根据请求流水号更新请求状态
     *
     * @param requestNo
     * @param requestsStatus
     * @return
     */
    Boolean modifyRequestStatus(String requestNo, Integer requestsStatus);

    /**
     * 保存标的
     *
     * @param projectDTO
     * @return
     */
    DepositoryResponseDTO<DepositoryBaseResponse> createProject(ProjectDTO
                                                                        projectDTO);

    /**
     * 投标预处理
     *
     * @param userAutoPreTransactionRequest
     * @return
     */
    DepositoryResponseDTO<DepositoryBaseResponse> userAutoPreTransaction(UserAutoPreTransactionRequest userAutoPreTransactionRequest);

    /**
     * 审核满标放款
     *
     * @param loanRequest
     * @return
     */
    DepositoryResponseDTO<DepositoryBaseResponse> confirmLoan(LoanRequest loanRequest);

    /**
     * 修改标的状态
     *
     * @param modifyProjectStatusDTO
     * @return
     */
    DepositoryResponseDTO<DepositoryBaseResponse>
    modifyProjectStatus(ModifyProjectStatusDTO modifyProjectStatusDTO);

    /**
     * 还款确认
     *
     * @param repaymentRequest
     * @return
     */
    DepositoryResponseDTO<DepositoryBaseResponse> confirmRepayment(RepaymentRequest repaymentRequest);
}
