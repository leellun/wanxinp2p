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
 * 银行用户信息表
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="BankUser对象", description="银行用户信息表")
public class BankUser implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "真实姓名")
    @TableField("FULLNAME")
    private String fullname;

    @ApiModelProperty(value = "身份证号")
    @TableField("ID_NUMBER")
    private String idNumber;

    @ApiModelProperty(value = "银行预留手机号")
    @TableField("MOBILE")
    private String mobile;

    @ApiModelProperty(value = "用户类型,个人1or企业0，预留")
    @TableField("USER_TYPE")
    private Integer userType;


}
