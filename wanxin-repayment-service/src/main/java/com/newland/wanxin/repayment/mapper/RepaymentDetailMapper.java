package com.newland.wanxin.repayment.mapper;

import com.newland.wanxin.repayment.entity.RepaymentDetail;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 借款人还款明细，针对一个还款计划可多次进行还款 Mapper 接口
 * </p>
 *
 * @author leellun
 * @since 2022-11-21
 */
@Repository
public interface RepaymentDetailMapper extends BaseMapper<RepaymentDetail> {

}
