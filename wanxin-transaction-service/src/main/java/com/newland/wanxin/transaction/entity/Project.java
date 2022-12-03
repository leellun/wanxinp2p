package com.newland.wanxin.transaction.entity;

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
 * 标的信息表
 * </p>
 *
 * @author leellun
 * @since 2022-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Project对象", description="标的信息表")
public class Project implements Serializable {

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

    @ApiModelProperty(value = "标的编码")
    @TableField("PROJECT_NO")
    private String projectNo;

    @ApiModelProperty(value = "标的名称")
    @TableField("NAME")
    private String name;

    @ApiModelProperty(value = "标的描述")
    @TableField("DESCRIPTION")
    private String description;

    @ApiModelProperty(value = "标的类型")
    @TableField("TYPE")
    private String type;

    @ApiModelProperty(value = "标的期限(单位:天)")
    @TableField("PERIOD")
    private Integer period;

    @ApiModelProperty(value = "年化利率(投资人视图)")
    @TableField("ANNUAL_RATE")
    private BigDecimal annualRate;

    @ApiModelProperty(value = "年化利率(借款人视图)")
    @TableField("BORROWER_ANNUAL_RATE")
    private BigDecimal borrowerAnnualRate;

    @ApiModelProperty(value = "年化利率(平台佣金，利差)")
    @TableField("COMMISSION_ANNUAL_RATE")
    private BigDecimal commissionAnnualRate;

    @ApiModelProperty(value = "还款方式")
    @TableField("REPAYMENT_WAY")
    private String repaymentWay;

    @ApiModelProperty(value = "募集金额")
    @TableField("AMOUNT")
    private BigDecimal amount;

    @ApiModelProperty(value = "标的状态")
    @TableField("PROJECT_STATUS")
    private String projectStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "可用状态")
    @TableField("STATUS")
    private Integer status;

    @ApiModelProperty(value = "是否是债权出让标")
    @TableField("IS_ASSIGNMENT")
    private Integer isAssignment;

    @ApiModelProperty(value = "发标请求流水号")
    @TableField("REQUEST_NO")
    private String requestNo;
}
