package com.newland.wanxin.repayment.entity;

import java.math.BigDecimal;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 借款人还款明细，针对一个还款计划可多次进行还款
 * </p>
 *
 * @author leellun
 * @since 2022-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RepaymentDetail对象", description="借款人还款明细，针对一个还款计划可多次进行还款")
public class RepaymentDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "还款计划项标识")
    @TableField("REPAYMENT_PLAN_ID")
    private Long repaymentPlanId;

    @ApiModelProperty(value = "实还本息")
    @TableField("AMOUNT")
    private BigDecimal amount;

    @ApiModelProperty(value = "实际还款时间")
    @TableField("REPAYMENT_DATE")
    private LocalDateTime repaymentDate;

    @ApiModelProperty(value = "冻结用户资金请求流水号(用于解冻合并整体还款)，	            有漏洞，存管不支持单次“确定还款”，合并多个还款预处理的操作，折中做法。")
    @TableField("REQUEST_NO")
    private String requestNo;

    @ApiModelProperty(value = "可用状态")
    @TableField("STATUS")
    private Integer status;


}
