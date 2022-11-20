package com.newland.wanxin.depository.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.newland.wanxin.depository.common.constant.StatusCode;
import com.newland.wanxin.depository.entity.RequestDetails;
import com.newland.wanxin.depository.mapper.RequestDetailsMapper;
import com.newland.wanxin.depository.model.response.BaseResponse;
import com.newland.wanxin.depository.service.RequestDetailsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 存管系统请求信息表 服务实现类
 * </p>
 *
 * @author leellun
 * @since 2022-11-20
 */
@Service
public class RequestDetailsServiceImpl extends ServiceImpl<RequestDetailsMapper, RequestDetails> implements RequestDetailsService {
    @Override
    public RequestDetails create(RequestDetails requestDetails) {
        requestDetails.setStatus(StatusCode.STATUS_OUT.getCode());
        save(requestDetails);
        return requestDetails;
    }

    @Override
    public Boolean modifyByRequestNo(RequestDetails requestDetails) {
        return update(requestDetails, new QueryWrapper<RequestDetails>().lambda()
                .eq(RequestDetails::getRequestNo, requestDetails.getRequestNo()));
    }

    @Override
    public Boolean modifyGatewayByRequestNo(BaseResponse response) {
        RequestDetails requestDetails = new RequestDetails();
        requestDetails.setRequestNo(response.getRequestNo());
        requestDetails.setStatus(response.getStatus());
        requestDetails.setResponseData(JSON.toJSONString(response));
        return modifyByRequestNo(requestDetails);
    }

    @Override
    public RequestDetails getByRequestNo(String requestNo) {
        return getOne(new QueryWrapper<RequestDetails>().lambda().eq(RequestDetails::getRequestNo, requestNo), false);
    }
}
