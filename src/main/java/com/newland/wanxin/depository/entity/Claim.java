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
 * 债权转让标的附加信息
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="Claim对象", description="债权转让标的附加信息")
public class Claim implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "标的标识")
    @TableField("PROJECT_ID")
    private Long projectId;

    @ApiModelProperty(value = "标的编码")
    @TableField("PROJECT_NO")
    private String projectNo;

    @ApiModelProperty(value = "发标人用户标识(冗余)")
    @TableField("CONSUMER_ID")
    private Long consumerId;

    @ApiModelProperty(value = "投标信息标识(转让来源)")
    @TableField("SOURCE_TENDER_ID")
    private Long sourceTenderId;

    @ApiModelProperty(value = "原始标的标识(冗余)")
    @TableField("ROOT_PROJECT_ID")
    private Long rootProjectId;

    @ApiModelProperty(value = "原始标的编码(冗余)")
    @TableField("ROOT_PROJECT_NO")
    private String rootProjectNo;

    @ApiModelProperty(value = "债权转让 请求流水号")
    @TableField("ASSIGNMENT_REQUEST_NO")
    private String assignmentRequestNo;


}
