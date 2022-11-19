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
 * 菜单
 * </p>
 *
 * @author leellun
 * @since 2022-11-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Menu对象", description="菜单")
public class Menu implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "父id")
    @TableField("PARENT_ID")
    private Long parentId;

    @ApiModelProperty(value = "菜单标题")
    @TableField("TITLE")
    private String title;

    @ApiModelProperty(value = "链接url")
    @TableField("URL")
    private String url;

    @ApiModelProperty(value = "图标")
    @TableField("ICON")
    private String icon;

    @ApiModelProperty(value = "排序")
    @TableField("SORT")
    private Integer sort;

    @ApiModelProperty(value = "说明")
    @TableField("COMMENT")
    private String comment;

    @ApiModelProperty(value = "状态")
    @TableField("STATUS")
    private Integer status;

    @ApiModelProperty(value = "绑定权限")
    @TableField("PRIVILEGE_CODE")
    private String privilegeCode;


}
