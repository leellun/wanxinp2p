package com.newland.wanxin.api.transaction.model;

import lombok.Data;

/**
 * <P>
 * 修改标的状态DTO
 * </p>
 *
 * @author wuzhao@itcast.cn
 * @since 2019/5/24
 */
@Data
public class ModifyProjectStatusDTO {
    /**
     * 请求流水号
     */
    private String requestNo;
    /**
     * 标的号
     */
    private String projectNo;
    /**
     * 更新标的状态
     */
    private String projectStatus;

    /**
     * 业务实体id
     */
    private Long id;
}
