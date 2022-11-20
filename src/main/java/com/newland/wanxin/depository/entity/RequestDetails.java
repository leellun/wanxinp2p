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
 * 存管系统请求信息表
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@ApiModel(value="RequestDetails对象", description="存管系统请求信息表")
public class RequestDetails implements Serializable {

    private static final long serialVersionUID=1L;

    @TableId(value = "ID", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "应用编码")
    @TableField("APP_CODE")
    private String appCode;

    @TableField("REQUEST_NO")
    private String requestNo;

    @ApiModelProperty(value = "请求类型:1.用户信息、2.绑卡信息")
    @TableField("SERVICE_NAME")
    private String serviceName;

    @TableField("REQUEST_DATA")
    private String requestData;

    @TableField("RESPONSE_DATA")
    private String responseData;

    @ApiModelProperty(value = "执行结果")
    @TableField("STATUS")
    private Integer status;

    @ApiModelProperty(value = "请求时间")
    @TableField("CREATE_DATE")
    private LocalDateTime createDate;

    @ApiModelProperty(value = "执行返回时间")
    @TableField("FINISH_DATE")
    private LocalDateTime finishDate;


}
