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
 * 投资人应收明细
 * </p>
 *
 * @author leellun
 * @since 2022-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ReceivablePlan对象", description="投资人应收明细")
public class ReceivablePlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "投标人用户标识")
    @TableField("CONSUMER_ID")
    private Long consumerId;

    @ApiModelProperty(value = "投标人用户编码")
    @TableField("USER_NO")
    private String userNo;

    @ApiModelProperty(value = "投标信息标识")
    @TableField("TENDER_ID")
    private Long tenderId;

    @ApiModelProperty(value = "还款计划项标识")
    @TableField("REPAYMENT_ID")
    private Long repaymentId;

    @ApiModelProperty(value = "期数")
    @TableField("NUMBER_OF_PERIODS")
    private Integer numberOfPeriods;

    @ApiModelProperty(value = "应收利息")
    @TableField("INTEREST")
    private BigDecimal interest;

    @ApiModelProperty(value = "应收本金")
    @TableField("PRINCIPAL")
    private BigDecimal principal;

    @ApiModelProperty(value = "应收本息")
    @TableField("AMOUNT")
    private BigDecimal amount;

    @ApiModelProperty(value = "应收时间")
    @TableField("SHOULD_RECEIVABLE_DATE")
    private LocalDateTime shouldReceivableDate;

    @ApiModelProperty(value = "状态：0,.未收 1.已收  2.部分收到")
    @TableField("RECEIVABLE_STATUS")
    private Integer receivableStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "年化利率(平台佣金，利差)")
    @TableField("COMMISSION")
    private BigDecimal commission;


}
