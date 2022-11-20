package com.newland.wanxin.depository.mapper;

import com.newland.wanxin.depository.entity.BankCardDetails;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 银行卡明细 Mapper 接口
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Repository
public interface BankCardDetailsMapper extends BaseMapper<BankCardDetails> {
    /**
     * 根据银行卡ID获取余额
     * @param bankCardId
     * @return
     */
    BankCardDetails selectByBankCardId(Long bankCardId);
}
