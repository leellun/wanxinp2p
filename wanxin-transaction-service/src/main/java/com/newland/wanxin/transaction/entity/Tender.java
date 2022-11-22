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
 * 投标信息表
 * </p>
 *
 * @author leellun
 * @since 2022-11-22
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="Tender对象", description="投标信息表")
public class Tender implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "投标人用户标识")
    @TableField("CONSUMER_ID")
    private Long consumerId;

    @ApiModelProperty(value = "投标人用户名")
    @TableField("CONSUMER_USERNAME")
    private String consumerUsername;

    @ApiModelProperty(value = "投标人用户编码")
    @TableField("USER_NO")
    private String userNo;

    @ApiModelProperty(value = "标的标识")
    @TableField("PROJECT_ID")
    private Long projectId;

    @ApiModelProperty(value = "标的编码")
    @TableField("PROJECT_NO")
    private String projectNo;

    @ApiModelProperty(value = "投标冻结金额")
    @TableField("AMOUNT")
    private BigDecimal amount;

    @ApiModelProperty(value = "投标状态")
    @TableField("TENDER_STATUS")
    private String tenderStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "投标/债权转让 请求流水号")
    @TableField("REQUEST_NO")
    private String requestNo;

    @ApiModelProperty(value = "可用状态")
    @TableField("STATUS")
    private Integer status;

    @ApiModelProperty(value = "标的名称")
    @TableField("PROJECT_NAME")
    private String projectName;

    @ApiModelProperty(value = "标的期限(单位:天) -- 冗余字段")
    @TableField("PROJECT_PERIOD")
    private Integer projectPeriod;

    @ApiModelProperty(value = "年化利率(投资人视图) -- 冗余字段")
    @TableField("PROJECT_ANNUAL_RATE")
    private BigDecimal projectAnnualRate;


}
