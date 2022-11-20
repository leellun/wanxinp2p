package com.newland.wanxin.consumer.service;

import com.newland.wanxin.api.consumer.model.BankCardDTO;
import com.newland.wanxin.consumer.entity.BankCard;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 用户绑定银行卡信息 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
public interface BankCardService extends IService<BankCard> {
    /**
     * 获取银行卡信息
     * @param consumerId 用户id
     * @return
     */
    BankCardDTO getByConsumerId(Long consumerId);
    /**
     * 获取银行卡信息
     * @param cardNumber 卡号
     * @return
     */
    BankCardDTO getByCardNumber(String cardNumber);
}
