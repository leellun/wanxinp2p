package com.newland.wanxin.account.entity;

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
 * 账号-角色关系
 * </p>
 *
 * @author leellun
 * @since 2022-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="AccountRole对象", description="账号-角色关系")
public class AccountRole implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "账号标识")
    @TableField("ACCOUNT_ID")
    private Long accountId;

    @ApiModelProperty(value = "角色标识")
    @TableField("ROLE_ID")
    private Long roleId;


}
