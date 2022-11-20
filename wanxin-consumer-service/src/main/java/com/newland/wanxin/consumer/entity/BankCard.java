package com.newland.wanxin.consumer.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 用户绑定银行卡信息
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="BankCard对象", description="用户绑定银行卡信息")
public class BankCard implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户标识")
    @TableField("CONSUMER_ID")
    private Long consumerId;

    @ApiModelProperty(value = "银行编码")
    @TableField("BANK_CODE")
    private String bankCode;

    @ApiModelProperty(value = "银行名称")
    @TableField("BANK_NAME")
    private String bankName;

    @ApiModelProperty(value = "银行卡号")
    @TableField("CARD_NUMBER")
    private String cardNumber;

    @ApiModelProperty(value = "银行预留手机号")
    @TableField("MOBILE")
    private String mobile;

    @ApiModelProperty(value = "可用状态")
    @TableField("STATUS")
    private Integer status;


}
