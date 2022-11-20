package com.newland.wanxin.depository.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.wanxin.depository.common.constant.BalanceChangeCode;
import com.newland.wanxin.depository.common.domain.BusinessException;
import com.newland.wanxin.depository.common.domain.RemoteReturnCode;
import com.newland.wanxin.depository.common.util.EncryptUtil;
import com.newland.wanxin.depository.entity.BalanceDetails;
import com.newland.wanxin.depository.entity.Project;
import com.newland.wanxin.depository.mapper.BalanceDetailsMapper;
import com.newland.wanxin.depository.model.dto.BalanceDetailsDTO;
import com.newland.wanxin.depository.model.request.*;
import com.newland.wanxin.depository.model.response.ConfirmRepaymentResponse;
import com.newland.wanxin.depository.model.response.UserAutoPreTransactionResponse;
import com.newland.wanxin.depository.service.BalanceDetailsService;
import com.newland.wanxin.depository.service.ProjectService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

/**
 * <p>
 * 用户余额明细表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Service
public class BalanceDetailsServiceImpl extends ServiceImpl<BalanceDetailsMapper, BalanceDetails> implements BalanceDetailsService {
    @Autowired
    private ProjectService projectService;

    @Override
    public BalanceDetailsDTO getP2PBalanceDetails(String userNo) {
        BalanceDetails balanceDetails = getByUserNo(userNo);
        BalanceDetailsDTO dto = new BalanceDetailsDTO();
        if (balanceDetails != null) {
            BeanUtils.copyProperties(balanceDetails, dto);
        } else {
            dto.setBalance(new BigDecimal(0));
        }
        return dto;
    }

    @Override
    public UserAutoPreTransactionResponse verifyEnough(String userNo, BigDecimal needAmount, UserAutoPreTransactionResponse response) {
        if (!isEnough(userNo, needAmount)) {
            //余额不足返回异常信息
            response.setResp(RemoteReturnCode.BALANCE_NOT_ENOUGH);
            return response;
        }
        return null;
    }

    @Override
    public Boolean freezeBalance(UserAutoPreTransactionRequest preTransactionRequest) {
        //之前用户余额信息
        BalanceDetails prevBalanceDetails=getByUserNo(preTransactionRequest.getUserNo());

        //最新用户余额信息
        BalanceDetails newBalanceDetails=new BalanceDetails();
        newBalanceDetails.setUserNo(prevBalanceDetails.getUserNo());
        newBalanceDetails.setAmount(prevBalanceDetails.getAmount());
        newBalanceDetails.setRequestContent(JSON.toJSONString(prevBalanceDetails));
        newBalanceDetails.setAppCode(preTransactionRequest.getAppCode());
        newBalanceDetails.setChangeType(BalanceChangeCode.FREEZE.getCode());

        //累加冻结金额
        BigDecimal freezeAmount=prevBalanceDetails.getFreezeAmount().add(prevBalanceDetails.getAmount());
        newBalanceDetails.setFreezeAmount(freezeAmount);

        //最新可用余额= 之前可用余额 - 本次请求金额
        BigDecimal balance = prevBalanceDetails.getBalance().subtract(preTransactionRequest.getAmount());
        newBalanceDetails.setBalance(balance);
        return save(newBalanceDetails);
    }

    @Override
    public Boolean increaseBalance(BalanceDetails balanceDetails, BigDecimal amount) {
        balanceDetails.setId(null);
        balanceDetails.setChangeType(BalanceChangeCode.INCREASE.getCode());
        balanceDetails.setAmount(amount);

        //增加用户余额
        BigDecimal balance = balanceDetails.getBalance().add(amount);
        balanceDetails.setBalance(balance);
        return save(balanceDetails);
    }

    @Override
    public Boolean decreaseBalance(BalanceDetails balanceDetails, BigDecimal amount) {
        balanceDetails.setId(null);
        balanceDetails.setChangeType(BalanceChangeCode.DECREASE.getCode());
        balanceDetails.setAmount(amount);

        //减少冻结金额
        BigDecimal freezeAmount = balanceDetails.getFreezeAmount().subtract(amount);
        balanceDetails.setFreezeAmount(freezeAmount);
        return save(balanceDetails);
    }

    @Override
    public Boolean recharge(RechargeRequest rechargeRequest) {
        String userNo = rechargeRequest.getUserNo();
        //获取充值之前用户余额信息
        BalanceDetails balanceDetails = getByUserNo(userNo);

        //充值后余额信息
        BalanceDetails newBalanceDetails = new BalanceDetails();
        newBalanceDetails.setUserNo(userNo);
        newBalanceDetails.setAppCode(rechargeRequest.getAppCode());
        newBalanceDetails.setChangeType(BalanceChangeCode.INCREASE.getCode());
        newBalanceDetails.setRequestContent(JSON.toJSONString(rechargeRequest));
        //变动金额
        newBalanceDetails.setAmount(rechargeRequest.getAmount());
        //冻结金额
        newBalanceDetails.setFreezeAmount(balanceDetails.getFreezeAmount());
        //可用余额：添加充值金额
        newBalanceDetails.setBalance(balanceDetails.getBalance().add(rechargeRequest.getAmount()));
        return save(newBalanceDetails);
    }

    @Override
    public Boolean withDraw(WithdrawRequest withDrawRequest) {
        String userNo = withDrawRequest.getUserNo();
        //获取提现之前用户余额信息
        BalanceDetails balanceDetails = getByUserNo(userNo);

        //提现后余额信息
        BalanceDetails newBalanceDetails = new BalanceDetails();
        newBalanceDetails.setUserNo(userNo);
        newBalanceDetails.setAppCode(withDrawRequest.getAppCode());
        newBalanceDetails.setChangeType(BalanceChangeCode.DECREASE.getCode());
        newBalanceDetails.setRequestContent(JSON.toJSONString(withDrawRequest));
        //变动金额
        newBalanceDetails.setAmount(withDrawRequest.getAmount());
        //冻结金额
        newBalanceDetails.setFreezeAmount(balanceDetails.getFreezeAmount());
        //可用余额：扣除提现金额
        newBalanceDetails.setBalance(balanceDetails.getBalance().subtract(withDrawRequest.getAmount()));
        return save(newBalanceDetails);
    }

    @Override
    public Boolean addForPersonalRegister(BalanceDetails balanceDetails) {
        BigDecimal defaultBalance = new BigDecimal("0");
        balanceDetails.setChangeType(BalanceChangeCode.NEW.getCode());
        balanceDetails.setAmount(defaultBalance);
        balanceDetails.setFreezeAmount(defaultBalance);
        balanceDetails.setBalance(defaultBalance);
        return save(balanceDetails);
    }

    @Override
    public UserAutoPreTransactionResponse autoPreTransactionForRepayment(UserAutoPreTransactionRequest preTransactionRequest) {
        String requestNo = preTransactionRequest.getRequestNo();
        UserAutoPreTransactionResponse response = new UserAutoPreTransactionResponse();
        response.setRequestNo(requestNo);
        response.setBizType(preTransactionRequest.getBizType());

        // 校验用户余额是否够用
        UserAutoPreTransactionResponse verifyResponse = verifyEnough(preTransactionRequest.getUserNo(),
                preTransactionRequest.getAmount(), response);
        if (verifyResponse != null) {
            //返回余额不足错误信息
            return verifyResponse;
        }

        //冻结用户余额
        freezeBalance(preTransactionRequest);
        response.setSuccess();
        return response;
    }

    @Override
    public ConfirmRepaymentResponse confirmRepayment(String reqData) {
        String decodeReqData = EncryptUtil.decodeUTF8StringBase64(reqData);
        ConfirmRepaymentRequest request = JSON.parseObject(decodeReqData, ConfirmRepaymentRequest.class);
        String requestNo = request.getRequestNo();

        ConfirmRepaymentResponse response = new ConfirmRepaymentResponse();
        response.setRequestNo(requestNo);

        List<ConfirmRepaymentDetailRequest> repaymentDetailList = request.getDetails();
        try {
            //扣除借款人还款本息
            Project project = projectService.getByProjectNo(request.getProjectNo());
            BalanceDetails loanBalanceDetails = getByUserNo(project.getUserNo());
            loanBalanceDetails.setAppCode(request.getAppCode());
            loanBalanceDetails.setRequestContent(decodeReqData);
            decreaseBalance(loanBalanceDetails, request.getAmount());

            //增加还款本息到投资人账户
            for (ConfirmRepaymentDetailRequest repayment : repaymentDetailList) {
                BalanceDetails investBalanceDetails = getByUserNo(repayment.getUserNo());
                investBalanceDetails.setAppCode(request.getAppCode());
                investBalanceDetails.setRequestContent(decodeReqData);
                increaseBalance(investBalanceDetails, repayment.getAmount().add(repayment.getInterest()));
            }
            response.setSuccess();
        } catch (Exception e) {
            log.error(e.getMessage());
            response.setFailure();
            throw new BusinessException(requestNo, RemoteReturnCode.EXCEPTION);
        }
        return response;
    }

    @Override
    public BalanceDetails getByUserNo(String userNo) {
        return getOne(new QueryWrapper<BalanceDetails>().lambda().eq(BalanceDetails::getUserNo, userNo)
                .orderByDesc(BalanceDetails::getId).last("LIMIT 1"));
    }

    /**
     * 判断用户余额是否充足
     *
     * @param userNo
     * @param needAmount
     * @return
     */
    private Boolean isEnough(String userNo, BigDecimal needAmount) {
        BalanceDetails details = getByUserNo(userNo);
        if (details == null || details.getBalance().compareTo(needAmount) < 0) {
            return false;
        }
        return true;
    }
}
