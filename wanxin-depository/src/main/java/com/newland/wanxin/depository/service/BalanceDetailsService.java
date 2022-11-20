package com.newland.wanxin.depository.service;

import com.newland.wanxin.depository.entity.BalanceDetails;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.wanxin.depository.model.dto.BalanceDetailsDTO;
import com.newland.wanxin.depository.model.request.RechargeRequest;
import com.newland.wanxin.depository.model.request.UserAutoPreTransactionRequest;
import com.newland.wanxin.depository.model.request.WithdrawRequest;
import com.newland.wanxin.depository.model.response.ConfirmRepaymentResponse;
import com.newland.wanxin.depository.model.response.UserAutoPreTransactionResponse;

import java.math.BigDecimal;

/**
 * <p>
 * 用户余额明细表 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
public interface BalanceDetailsService extends IService<BalanceDetails> {
    /**
     * 根据用户编码获取余额信息
     * @param userNo
     * @return
     */
    BalanceDetailsDTO getP2PBalanceDetails(String userNo);

    /**
     * 校验用户余额是否充足
     * @param userNo
     * @param needAmount
     * @param response 若为null则余额充足，否则返回异常response
     * @return
     */
    UserAutoPreTransactionResponse verifyEnough(String userNo, BigDecimal needAmount, UserAutoPreTransactionResponse response);

    /**
     * 根据预处理请求信息冻结用户金额
     * @param preTransactionRequest
     * @return
     */
    Boolean freezeBalance(UserAutoPreTransactionRequest preTransactionRequest);

    /**
     * 增加用户余额
     * @param balanceDetails
     * @param amount
     * @return
     */
    Boolean increaseBalance(BalanceDetails balanceDetails, BigDecimal amount);

    /**
     * 扣减用户余额
     * @param balanceDetails
     * @param amount
     * @return
     */
    Boolean decreaseBalance(BalanceDetails balanceDetails, BigDecimal amount);

    /**
     * 用户充值
     * @param rechargeRequest
     * @return
     */
    Boolean recharge(RechargeRequest rechargeRequest);

    /**
     * 用户提现
     * @param withDrawRequest
     * @return
     */
    Boolean withDraw(WithdrawRequest withDrawRequest);


    /**
     * 根据用户编号获取余额信息
     * @param userNo
     * @return
     */
    BalanceDetails getByUserNo(String userNo);

    /**
     * 用户开户添加初始化余额信息
     * @param balanceDetails
     * @return
     */
    Boolean addForPersonalRegister(BalanceDetails balanceDetails);

    /**
     * 还款确认：预处理冻结金额
     * @param preTransactionRequest
     * @return
     */
    UserAutoPreTransactionResponse autoPreTransactionForRepayment(UserAutoPreTransactionRequest preTransactionRequest);

    /**
     * 放款确认
     * @param reqData
     * @return
     */
    ConfirmRepaymentResponse confirmRepayment(String reqData);
}
