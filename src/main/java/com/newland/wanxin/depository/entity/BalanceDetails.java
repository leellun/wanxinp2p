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
 * 用户余额明细表
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="BalanceDetails对象", description="用户余额明细表")
public class BalanceDetails implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户编码,生成唯一,用户在存管系统标识")
    @TableField("USER_NO")
    private String userNo;

    @ApiModelProperty(value = "账户变动类型.1.增加.2.减少.3.冻结.4解冻")
    @TableField("CHANGE_TYPE")
    private Integer changeType;

    @ApiModelProperty(value = "变动金额")
    @TableField("AMOUNT")
    private BigDecimal amount;

    @ApiModelProperty(value = "冻结金额")
    @TableField("FREEZE_AMOUNT")
    private BigDecimal freezeAmount;

    @ApiModelProperty(value = "可用余额")
    @TableField("BALANCE")
    private BigDecimal balance;

    @ApiModelProperty(value = "应用编码")
    @TableField("APP_CODE")
    private String appCode;

    @ApiModelProperty(value = "账户变动时间")
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "引起余额变动的冗余业务信息")
    @TableField("REQUEST_CONTENT")
    private String requestContent;


}
