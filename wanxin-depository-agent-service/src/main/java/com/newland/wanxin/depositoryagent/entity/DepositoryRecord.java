package com.newland.wanxin.depositoryagent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableField;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

/**
 * <p>
 * 存管交易记录表
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="DepositoryRecord对象", description="存管交易记录表")
public class DepositoryRecord implements Serializable {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "主键")
    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "请求流水号")
    @TableField("REQUEST_NO")
    private String requestNo;

    @ApiModelProperty(value = "请求类型:1.用户信息(新增、编辑)、2.绑卡信息")
    @TableField("REQUEST_TYPE")
    private String requestType;

    @ApiModelProperty(value = "业务实体类型")
    @TableField("OBJECT_TYPE")
    private String objectType;

    @ApiModelProperty(value = "关联业务实体标识")
    @TableField("OBJECT_ID")
    private Long objectId;

    @ApiModelProperty(value = "请求时间")
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "是否是同步调用")
    @TableField("IS_SYN")
    private Integer syn;

    @ApiModelProperty(value = "数据同步状态")
    @TableField("REQUEST_STATUS")
    private Integer requestStatus;

    @ApiModelProperty(value = "消息确认时间")
    @TableField("CONFIRM_DATE")
    private LocalDateTime confirmDate;
    /**
     * 返回数据
     */
    @ApiModelProperty(value = "返回数据")
    @TableField("RESPONSE_DATA")
    private String responseData;

    public DepositoryRecord(String requestNo, String requestType, String objectType, Long objectId) {
        this.requestNo = requestNo;
        this.requestType = requestType;
        this.objectType = objectType;
        this.objectId = objectId;
    }
}
