package com.newland.wanxin.consumer.entity;

import java.math.BigDecimal;
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
 * c端用户信息表
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Consumer对象", description="c端用户信息表")
public class Consumer implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    @TableField("USERNAME")
    private String username;

    @ApiModelProperty(value = "真实姓名")
    @TableField("FULLNAME")
    private String fullname;

    @ApiModelProperty(value = "身份证号")
    @TableField("ID_NUMBER")
    private String idNumber;

    @ApiModelProperty(value = "用户编码,生成唯一,用户在存管系统标识")
    @TableField("USER_NO")
    private String userNo;

    @ApiModelProperty(value = "平台预留手机号")
    @TableField("MOBILE")
    private String mobile;

    @ApiModelProperty(value = "用户类型,个人or企业，预留")
    @TableField("USER_TYPE")
    private String userType;

    @ApiModelProperty(value = "用户角色.B借款人or I投资人")
    @TableField("ROLE")
    private String role;

    @ApiModelProperty(value = "存管授权列表")
    @TableField("AUTH_LIST")
    private String authList;

    @ApiModelProperty(value = "是否已绑定银行卡")
    @TableField("IS_BIND_CARD")
    private Integer bindCard;

    @TableField("LOAN_AMOUNT")
    private BigDecimal loanAmount;

    @ApiModelProperty(value = "可用状态")
    @TableField("STATUS")
    private Integer status;

    @ApiModelProperty(value = "请求流水号")
    @TableField("REQUEST_NO")
    private String requestNo;


}
