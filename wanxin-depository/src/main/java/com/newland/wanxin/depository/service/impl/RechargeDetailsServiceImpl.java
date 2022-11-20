package com.newland.wanxin.depository.service.impl;

import com.newland.wanxin.depository.common.constant.StatusCode;
import com.newland.wanxin.depository.common.domain.BusinessException;
import com.newland.wanxin.depository.common.domain.LocalReturnCode;
import com.newland.wanxin.depository.common.domain.RemoteReturnCode;
import com.newland.wanxin.depository.entity.DepositoryBankCard;
import com.newland.wanxin.depository.entity.RechargeDetails;
import com.newland.wanxin.depository.mapper.RechargeDetailsMapper;
import com.newland.wanxin.depository.model.request.RechargeRequest;
import com.newland.wanxin.depository.model.response.RechargeResponse;
import com.newland.wanxin.depository.service.*;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 充值记录表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Service
public class RechargeDetailsServiceImpl extends ServiceImpl<RechargeDetailsMapper, RechargeDetails> implements RechargeDetailsService {
    @Autowired
    private BalanceDetailsService balanceDetailsService;

    @Autowired
    private UserService userService;

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private RequestDetailsService requestDetailsService;

    @Override
    public RechargeResponse recharge(RechargeRequest rechargeRequest) {
        String requestNo = rechargeRequest.getRequestNo();
        RechargeResponse response = new RechargeResponse();
        response.setRequestNo(requestNo);

        //校验交易密码
        if (!userService.verifyPassword(rechargeRequest.getUserNo(), rechargeRequest.getPassword())) {
            throw new BusinessException(LocalReturnCode.E_200302.getDesc());
        }

        //保存充值记录
        RechargeDetails rechargeDetails = new RechargeDetails();
        BeanUtils.copyProperties(rechargeRequest, rechargeDetails);
        rechargeDetails.setStatus(StatusCode.STATUS_OUT.getCode());
        save(rechargeDetails);

        try {
            //扣除用户实体银行卡余额
            DepositoryBankCard depositoryBankCard = userService.getDepositoryBankCardByUserNo(rechargeRequest.getUserNo());
            bankCardService.decreaseBalance(depositoryBankCard.getCardNumber(), rechargeRequest.getAmount());

            //更新用户在P2P平台可用余额信息
            balanceDetailsService.recharge(rechargeRequest);

            //更新充值记录结果
            rechargeDetails.setStatus(StatusCode.STATUS_IN.getCode());
            updateById(rechargeDetails);

            //更新处理结果
            response.setSuccess();
            requestDetailsService.modifyGatewayByRequestNo(response);

            //产生充值成功消息
            //producer.recharge(rechargeRequest.getAppCode(), response);
        } catch (Exception e) {
            log.error(e.getMessage(), e);

            //更新充值记录结果
            rechargeDetails.setStatus(StatusCode.STATUS_FAIL.getCode());
            updateById(rechargeDetails);

            //更新处理结果
            response.setFailure();
            response.setRespMsg(e.getMessage());
            requestDetailsService.modifyGatewayByRequestNo(response);

            //产生充值失败消息
            //producer.recharge(rechargeRequest.getAppCode(), response);
            throw new BusinessException(response.getRequestNo(), RemoteReturnCode.EXCEPTION, e.getMessage(), e);
        }
        return response;
    }
}
