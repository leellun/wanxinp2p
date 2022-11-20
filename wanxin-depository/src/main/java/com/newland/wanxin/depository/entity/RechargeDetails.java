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
 * 充值记录表
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RechargeDetails对象", description="充值记录表")
public class RechargeDetails implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "请求流水号")
    @TableField("REQUEST_NO")
    private String requestNo;

    @ApiModelProperty(value = "用户编码,生成唯一,用户在存管系统标识")
    @TableField("USER_NO")
    private String userNo;

    @ApiModelProperty(value = "金额")
    @TableField("AMOUNT")
    private BigDecimal amount;

    @ApiModelProperty(value = "触发时间")
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "执行结果")
    @TableField("STATUS")
    private Integer status;

    @ApiModelProperty(value = "执行返回时间")
    @TableField("FINISH_DATE")
    private LocalDateTime finishDate;

    @ApiModelProperty(value = "应用编码")
    @TableField("APP_CODE")
    private String appCode;


}
