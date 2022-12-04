package com.newland.wanxin.transaction.agent;

import com.newland.wanxin.api.depository.model.LoanRequest;
import com.newland.wanxin.api.depository.model.UserAutoPreTransactionRequest;
import com.newland.wanxin.api.transaction.model.ModifyProjectStatusDTO;
import com.newland.wanxin.api.transaction.model.ProjectDTO;
import com.newland.wanxin.domain.RestResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(value = "depository-agent-service")
public interface DepositoryAgentApiAgent {

    @PostMapping(value = "/l/createProject")
    public RestResponse<String> createProject(ProjectDTO projectDTO);

    @PostMapping("/l/user-auto-pre-transaction")
    RestResponse<String> userAutoPreTransaction(
            UserAutoPreTransactionRequest userAutoPreTransactionRequest);

    @PostMapping("/l/confirm-loan")
    RestResponse<String> confirmLoan(LoanRequest loanRequest);

    @PostMapping("/l/modify-project-status")
    RestResponse<String> modifyProjectStatus(ModifyProjectStatusDTO modifyProjectStatusDTO);
}
