package com.newland.wanxin.depository.entity;

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
import lombok.experimental.Accessors;

/**
 * <p>
 * 银行卡明细
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="BankCardDetails对象", description="银行卡明细")
public class BankCardDetails implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "银行卡ID")
    @TableField("BANK_CARD_ID")
    private Long bankCardId;

    @ApiModelProperty(value = "账户变动类型")
    @TableField("CHANGE_TYPE")
    private Integer changeType;

    @ApiModelProperty(value = "变动金额")
    @TableField("MONEY")
    private BigDecimal money;

    @ApiModelProperty(value = "当前余额")
    @TableField("BALANCE")
    private BigDecimal balance;

    @ApiModelProperty(value = "账户变动时间")
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;


}
