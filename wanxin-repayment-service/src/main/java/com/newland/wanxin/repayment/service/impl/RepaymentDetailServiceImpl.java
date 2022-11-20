package com.newland.wanxin.repayment.service.impl;

import com.newland.wanxin.repayment.entity.RepaymentDetail;
import com.newland.wanxin.repayment.mapper.RepaymentDetailMapper;
import com.newland.wanxin.repayment.service.RepaymentDetailService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 借款人还款明细，针对一个还款计划可多次进行还款 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-11-21
 */
@Service
public class RepaymentDetailServiceImpl extends ServiceImpl<RepaymentDetailMapper, RepaymentDetail> implements RepaymentDetailService {

}
