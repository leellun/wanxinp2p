package com.newland.wanxin.consumer.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.newland.wanxin.api.account.model.AccountDTO;
import com.newland.wanxin.api.account.model.AccountRegisterDTO;
import com.newland.wanxin.api.consumer.model.*;
import com.newland.wanxin.api.depository.GatewayRequest;
import com.newland.wanxin.api.depository.model.DepositoryConsumerResponse;
import com.newland.wanxin.consumer.agent.AccountApiAgent;
import com.newland.wanxin.consumer.agent.DepositoryAgentApiAgent;
import com.newland.wanxin.consumer.common.ConsumerErrorCode;
import com.newland.wanxin.consumer.entity.BankCard;
import com.newland.wanxin.consumer.entity.Consumer;
import com.newland.wanxin.consumer.mapper.ConsumerMapper;
import com.newland.wanxin.consumer.service.BankCardService;
import com.newland.wanxin.consumer.service.ConsumerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.wanxin.domain.*;
import com.newland.wanxin.enumeration.CodePrefixCode;
import com.newland.wanxin.enumeration.DepositoryReturnCode;
import com.newland.wanxin.enumeration.StatusCode;
import com.newland.wanxin.exception.BusinessException;
import com.newland.wanxin.enumeration.CommonErrorCode;
import com.newland.wanxin.utils.CodeNoUtil;
import com.newland.wanxin.utils.IDCardUtil;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.HmilyTCC;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Map;

/**
 * <p>
 * c端用户信息表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Service
@Slf4j
public class ConsumerServiceImpl extends ServiceImpl<ConsumerMapper, Consumer> implements ConsumerService {
    @Autowired
    private AccountApiAgent accountApiAgent;

    @Autowired
    private BankCardService bankCardService;

    @Autowired
    private DepositoryAgentApiAgent depositoryAgentApiAgent;

    @Override
    public Integer checkMobile(String mobile) {
        return getByMobile(mobile)!=null?1:0;
    }

    @Override
    public ConsumerDTO getByMobile(String mobile){
        Consumer consumer=getOne(new QueryWrapper<Consumer>().lambda().eq(Consumer::getMobile,mobile));
        return convertConsumerEntityToDTO(consumer);
    }

    @Override
    @HmilyTCC(confirmMethod = "confirmRegister",cancelMethod = "cancelRegister")
    public void register(ConsumerRegisterDTO consumerRegisterDTO) {
        if(checkMobile(consumerRegisterDTO.getMobile())==1){
            throw new BusinessException(ConsumerErrorCode.E_140107);
        }
        Consumer consumer=new Consumer();
        BeanUtils.copyProperties(consumerRegisterDTO, consumer);
        consumer.setUsername(CodeNoUtil.getNo(CodePrefixCode.CODE_NO_PREFIX));
        consumerRegisterDTO.setUsername(consumer.getUsername());
        consumer.setUserNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
        consumer.setBindCard(0);
        save(consumer);

        //远程调用account
        AccountRegisterDTO accountRegisterDTO=new AccountRegisterDTO();
        BeanUtils.copyProperties(consumerRegisterDTO, accountRegisterDTO);
        RestResponse<AccountDTO> restResponse=accountApiAgent.register(accountRegisterDTO);
        if(restResponse.getCode()!= CommonErrorCode.SUCCESS.getCode()){
            throw new BusinessException(ConsumerErrorCode.E_140106);
        }
    }

    @Override
    public RestResponse<GatewayRequest> createConsumer(ConsumerRequest consumerRequest) {
        //1.判断当前用户是否已经开户
        ConsumerDTO consumerDTO=getByMobile(consumerRequest.getMobile());
        if(consumerDTO.getIsBindCard()==1){
            throw new BusinessException(ConsumerErrorCode.E_140105);
        }

        //2.判断提交过来的银行卡是否已被绑定
        BankCardDTO bankCardDTO = bankCardService.getByCardNumber(consumerRequest.getCardNumber());
        if(bankCardDTO!=null && bankCardDTO.getStatus()== StatusCode.STATUS_IN.getCode()){
            throw  new BusinessException(ConsumerErrorCode.E_140151);
        }

        //3.更新用户的信息
        consumerRequest.setId(consumerDTO.getId());
        //产生请求流水号和用户编号
        consumerRequest.setUserNo(CodeNoUtil.getNo(CodePrefixCode.CODE_CONSUMER_PREFIX));
        consumerRequest.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
        //设置查询条件和需要更新的数据
        UpdateWrapper<Consumer> updateWrapper = new UpdateWrapper<>();
        updateWrapper.lambda().eq(Consumer::getMobile,consumerDTO.getMobile());
        updateWrapper.lambda().set(Consumer::getUserNo, consumerRequest.getUserNo());
        updateWrapper.lambda().set(Consumer::getRequestNo, consumerRequest.getRequestNo());
        updateWrapper.lambda().set(Consumer::getFullname, consumerRequest.getFullname());
        updateWrapper.lambda().set(Consumer::getIdNumber, consumerRequest.getIdNumber());
        updateWrapper.lambda().set(Consumer::getAuthList, "ALL");
        update(updateWrapper);

        //4.保存银行卡信息
        BankCard bankCard=new BankCard();
        bankCard.setConsumerId(consumerDTO.getId());
        bankCard.setBankCode(consumerRequest.getBankCode());
        bankCard.setCardNumber(consumerRequest.getCardNumber());
        bankCard.setMobile(consumerRequest.getMobile());
        bankCard.setStatus(StatusCode.STATUS_OUT.getCode());
        BankCardDTO existBankCard = bankCardService.getByConsumerId(bankCard.getConsumerId());
        if (existBankCard != null) {
            bankCard.setId(existBankCard.getId());
        }
        bankCardService.saveOrUpdate(bankCard);

        //5.准备数据，发起远程调用，把数据发到存管代理服务
        return depositoryAgentApiAgent.createConsumer(consumerRequest);
    }

    @Override
    @Transactional
    public Boolean modifyResult(DepositoryConsumerResponse response) {
        Consumer consumer = getByRequestNo(response.getRequestNo());
        int status = DepositoryReturnCode.RETURN_CODE_00000.getCode()
                .equals(response.getRespCode()) ? StatusCode.STATUS_IN.getCode()
                :StatusCode.STATUS_FAIL.getCode();
        //更新开户结果
        update(Wrappers.<Consumer>lambdaUpdate().eq(Consumer::getId, consumer.getId())
                .set(Consumer::getBindCard, status).set(Consumer::getStatus, status));
        //更新银行卡信息
        return bankCardService.update(Wrappers.<BankCard>lambdaUpdate()
                .eq(BankCard::getConsumerId, consumer.getId())
                .set(BankCard::getStatus, status).set(BankCard::getBankCode, response.getBankCode())
                .set(BankCard::getBankName, response.getBankName()));
    }

    @Override
    public BorrowerDTO getBorrower(Long id) {
        ConsumerDTO consumerDTO=get(id);
        BorrowerDTO borrowerDTO=new BorrowerDTO();
        BeanUtils.copyProperties(consumerDTO,borrowerDTO);

        Map<String,String> cardInfo= IDCardUtil.getInfo(borrowerDTO.getIdNumber());
        borrowerDTO.setAge(Integer.parseInt(cardInfo.get("age")));
        borrowerDTO.setBirthday(cardInfo.get("birthday"));
        borrowerDTO.setGender(cardInfo.get("gender"));
        return borrowerDTO;
    }

    private ConsumerDTO get(Long id) {
        Consumer entity = getById(id);
        if (entity == null) {
            log.info("id为{}的用户信息不存在", id);
            throw new BusinessException(ConsumerErrorCode.E_140101);
        }
        return convertConsumerEntityToDTO(entity);
    }
    public void confirmRegister(ConsumerRegisterDTO consumerRegisterDTO) {
        log.info("execute confirmRegister");
    }
    public void cancelRegister(ConsumerRegisterDTO consumerRegisterDTO) {
        log.info("execute cancelRegister");
        remove(Wrappers.<Consumer>lambdaQuery().eq(Consumer::getMobile,
                consumerRegisterDTO.getMobile()));
    }

    private Consumer getByRequestNo(String requestNo){
        return getOne(Wrappers.<Consumer>lambdaQuery().eq(Consumer::getRequestNo,requestNo));
    }
    /**
     * entity转为dto
     * @param entity
     * @return
     **/
    private ConsumerDTO convertConsumerEntityToDTO(Consumer entity) {
        if (entity == null) {
            return null;
        }
        ConsumerDTO dto = new ConsumerDTO();
        BeanUtils.copyProperties(entity, dto);
        return dto;
    }
}
