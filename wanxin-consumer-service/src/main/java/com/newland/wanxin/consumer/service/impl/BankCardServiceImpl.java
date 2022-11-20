package com.newland.wanxin.consumer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newland.wanxin.api.consumer.model.BankCardDTO;
import com.newland.wanxin.consumer.entity.BankCard;
import com.newland.wanxin.consumer.mapper.BankCardMapper;
import com.newland.wanxin.consumer.service.BankCardService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户绑定银行卡信息 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Service
public class BankCardServiceImpl extends ServiceImpl<BankCardMapper, BankCard> implements BankCardService {
    @Override
    public BankCardDTO getByConsumerId(Long consumerId) {
        BankCard bankCard = getOne(new QueryWrapper<BankCard>().lambda().eq(BankCard::getConsumerId, consumerId));
        return convertBankCardEntityToDTO(bankCard);
    }

    @Override
    public BankCardDTO getByCardNumber(String cardNumber) {
        BankCard bankCard = getOne(new QueryWrapper<BankCard>().lambda().eq(BankCard::getCardNumber, cardNumber));
        return convertBankCardEntityToDTO(bankCard);
    }

    private BankCardDTO convertBankCardEntityToDTO(BankCard bankCard) {
        if (bankCard == null) {
            return null;
        }
        BankCardDTO bankCardDTO = new BankCardDTO();
        BeanUtils.copyProperties(bankCard, bankCardDTO);
        return bankCardDTO;
    }
}
