package com.newland.wanxin.depository.entity;

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
 * 存管用户绑定银行卡信息
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DepositoryBankCard对象", description="存管用户绑定银行卡信息")
public class DepositoryBankCard implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户标识")
    @TableField("USER_ID")
    private Long userId;

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

    @ApiModelProperty(value = "应用编码")
    @TableField("APP_CODE")
    private String appCode;

    @ApiModelProperty(value = "请求流水号")
    @TableField("REQUEST_NO")
    private String requestNo;


}
