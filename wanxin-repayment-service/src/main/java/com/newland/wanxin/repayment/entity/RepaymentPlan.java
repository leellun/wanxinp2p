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
 * 借款人还款计划
 * </p>
 *
 * @author leellun
 * @since 2022-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="RepaymentPlan对象", description="借款人还款计划")
public class RepaymentPlan implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "发标人用户标识")
    @TableField("CONSUMER_ID")
    private Long consumerId;

    @ApiModelProperty(value = "发标人用户编码")
    @TableField("USER_NO")
    private String userNo;

    @ApiModelProperty(value = "标的标识")
    @TableField("PROJECT_ID")
    private Long projectId;

    @ApiModelProperty(value = "标的编码")
    @TableField("PROJECT_NO")
    private String projectNo;

    @ApiModelProperty(value = "期数")
    @TableField("NUMBER_OF_PERIODS")
    private Integer numberOfPeriods;

    @ApiModelProperty(value = "还款利息")
    @TableField("INTEREST")
    private BigDecimal interest;

    @ApiModelProperty(value = "还款本金")
    @TableField("PRINCIPAL")
    private BigDecimal principal;

    @ApiModelProperty(value = "本息")
    @TableField("AMOUNT")
    private BigDecimal amount;

    @ApiModelProperty(value = "应还时间")
    @TableField("SHOULD_REPAYMENT_DATE")
    private LocalDateTime shouldRepaymentDate;

    @ApiModelProperty(value = "应还状态0.待还,1.已还， 2.部分还款")
    @TableField("REPAYMENT_STATUS")
    private String repaymentStatus;

    @ApiModelProperty(value = "计划创建时间")
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "年化利率(平台佣金，利差)")
    @TableField("COMMISSION")
    private BigDecimal commission;


}
