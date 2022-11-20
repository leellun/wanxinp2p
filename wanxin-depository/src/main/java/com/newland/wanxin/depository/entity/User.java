package com.newland.wanxin.depository.entity;

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
 * 存管用户信息表
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="User对象", description="存管用户信息表")
public class User implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户编码,生成唯一,用户在存管系统标识")
    @TableField("USER_NO")
    private String userNo;

    @ApiModelProperty(value = "真实姓名")
    @TableField("FULLNAME")
    private String fullname;

    @ApiModelProperty(value = "身份证号")
    @TableField("ID_NUMBER")
    private String idNumber;

    @ApiModelProperty(value = "存管支付密码")
    @TableField("PASSWORD")
    private String password;

    @ApiModelProperty(value = "存管预留手机号")
    @TableField("MOBILE")
    private String mobile;

    @ApiModelProperty(value = "用户类型,个人or企业，预留")
    @TableField("USER_TYPE")
    private Integer userType;

    @ApiModelProperty(value = "用户角色")
    @TableField("ROLE")
    private String role;

    @ApiModelProperty(value = "授权列表")
    @TableField("AUTH_LIST")
    private String authList;

    @ApiModelProperty(value = "是否已绑定银行卡")
    @TableField("IS_BIND_CARD")
    private Integer isBindCard;

    @ApiModelProperty(value = "应用编码")
    @TableField("APP_CODE")
    private String appCode;

    @ApiModelProperty(value = "请求流水号")
    @TableField("REQUEST_NO")
    private String requestNo;

    @ApiModelProperty(value = "产生时间")
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;


}
