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
 * 投资人实收明细
 * </p>
 *
 * @author leellun
 * @since 2022-11-21
 */
@Data
@EqualsAndHashCode(callSuper = false)
@ApiModel(value="ReceivableDetail对象", description="投资人实收明细")
public class ReceivableDetail implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键")
      @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "应收项标识")
    @TableField("RECEIVABLE_ID")
    private Long receivableId;

    @ApiModelProperty(value = "实收本息")
    @TableField("AMOUNT")
    private BigDecimal amount;

    @ApiModelProperty(value = "实收时间")
    @TableField("RECEIVABLE_DATE")
    private LocalDateTime receivableDate;


}
