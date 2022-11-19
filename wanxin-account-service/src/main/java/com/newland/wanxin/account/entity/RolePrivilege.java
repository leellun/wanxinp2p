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
 * 角色-权限关系
 * </p>
 *
 * @author leellun
 * @since 2022-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RolePrivilege对象", description="角色-权限关系")
public class RolePrivilege implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "角色id")
    @TableField("ROLE_ID")
    private Long roleId;

    @ApiModelProperty(value = "权限id")
    @TableField("PRIVILEGE_ID")
    private Long privilegeId;


}
