package com.newland.wanxin.repayment.job;

import com.newland.wanxin.repayment.service.RepaymentPlanService;
import org.apache.shardingsphere.elasticjob.api.ShardingContext;
import org.apache.shardingsphere.elasticjob.simple.job.SimpleJob;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Component
public class RepaymentJob implements SimpleJob {

    @Autowired
    private RepaymentPlanService repaymentService;

    @Override
    public void execute(ShardingContext shardingContext) {
        //调用业务层执行还款任务
        repaymentService.executeRepayment(LocalDate.now().format(DateTimeFormatter.ISO_LOCAL_DATE),shardingContext.getShardingTotalCount(),shardingContext.getShardingItem());

        //调用业务层执行还款短信提醒
        repaymentService.sendRepaymentNotify(LocalDate.now().plusDays(1).format(DateTimeFormatter.ISO_LOCAL_DATE));

    }
}
