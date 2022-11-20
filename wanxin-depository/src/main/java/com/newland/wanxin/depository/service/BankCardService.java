package com.newland.wanxin.depository.service;

import com.newland.wanxin.depository.common.domain.PageVO;
import com.newland.wanxin.depository.entity.BankCard;
import com.baomidou.mybatisplus.extension.service.IService;
import com.newland.wanxin.depository.model.BankCardQuery;
import com.newland.wanxin.depository.model.dto.BankCardDTO;
import com.newland.wanxin.depository.model.request.BankCardRequest;

import java.math.BigDecimal;

/**
 * <p>
 * 银行用户银行卡信息 服务类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
public interface BankCardService extends IService<BankCard> {
    /**
     * 添加银行卡
     * @param bankCardRequest 银行卡注册信息
     * @return
     */
    String createBankCard(BankCardRequest bankCardRequest);

    /**
     * 根据卡号获取余额
     * @param cardNumber 银行卡号
     * @return
     */
    BigDecimal getBalance(String cardNumber);

    /**
     * 检索银行卡信息
     * @param bankCardQuery 检索参数
     * @param pageNo
     * @param pageSize
     * @param sortBy
     * @param order
     * @return
     */
    PageVO<BankCardDTO> queryBankCards(BankCardQuery bankCardQuery, Integer pageNo, Integer pageSize,
                                       String sortBy, String order);

    /**
     * 校验银行卡信息
     * @param bankCard
     * @return
     */
    Boolean verify(BankCard bankCard);

    /**
     * 增加余额
     * @param cardNumber 银行卡号
     * @param amount 金额
     * @return
     */
    Boolean increaseBalance(String cardNumber, BigDecimal amount);

    /**
     * 减少余额
     * @param cardNumber 银行卡号
     * @param amount 金额
     * @return
     */
    Boolean decreaseBalance(String cardNumber, BigDecimal amount);

    /**
     * 根据银行卡号获取
     * @param cardNumber
     * @return
     */
    BankCard getByCardNumber(String cardNumber);
}
