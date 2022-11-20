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
 * 投标信息表
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Tender对象", description="投标信息表")
public class Tender implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "投标人用户编码")
    @TableField("USER_NO")
    private String userNo;

    @ApiModelProperty(value = "标的编码")
    @TableField("PROJECT_NO")
    private String projectNo;

    @ApiModelProperty(value = "投标冻结金额")
    @TableField("AMOUNT")
    private BigDecimal amount;

    @ApiModelProperty(value = "投标状态")
    @TableField("TENDER_STATUS")
    private String tenderStatus;

    @ApiModelProperty(value = "创建时间")
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "修改时间")
    @TableField("MODIFY_DATE")
    private LocalDateTime modifyDate;

    @ApiModelProperty(value = "投标/债权转让 请求流水号")
    @TableField("REQUEST_NO")
    private String requestNo;

    @ApiModelProperty(value = "备注")
    @TableField("REMARK")
    private String remark;


}
