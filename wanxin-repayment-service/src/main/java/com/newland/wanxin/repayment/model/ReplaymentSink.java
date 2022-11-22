package com.newland.wanxin.repayment.model;

import org.springframework.cloud.stream.annotation.Input;
import org.springframework.messaging.SubscribableChannel;

/**
 * Author: leell
 * Date: 2022/11/22 19:41:21
 */
public interface ReplaymentSink {
    String INPUT = "replayment";

    @Input(INPUT)
    SubscribableChannel input();
}
