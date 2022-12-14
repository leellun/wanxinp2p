package com.newland.wanxin.transaction.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.newland.wanxin.api.consumer.model.BalanceDetailsDTO;
import com.newland.wanxin.api.consumer.model.ConsumerDTO;
import com.newland.wanxin.api.depository.model.LoanDetailRequest;
import com.newland.wanxin.api.depository.model.LoanRequest;
import com.newland.wanxin.api.depository.model.UserAutoPreTransactionRequest;
import com.newland.wanxin.api.repayment.model.ProjectWithTendersDTO;
import com.newland.wanxin.api.transaction.model.*;
import com.newland.wanxin.domain.*;
import com.newland.wanxin.enumeration.*;
import com.newland.wanxin.exception.BusinessException;
import com.newland.wanxin.enumeration.CommonErrorCode;
import com.newland.wanxin.transaction.agent.ConsumerApiAgent;
import com.newland.wanxin.transaction.agent.ContentSearchApiAgent;
import com.newland.wanxin.transaction.agent.DepositoryAgentApiAgent;
import com.newland.wanxin.transaction.common.constant.TradingCode;
import com.newland.wanxin.transaction.common.constant.TransactionErrorCode;
import com.newland.wanxin.transaction.common.utils.IncomeCalcUtil;
import com.newland.wanxin.transaction.common.utils.SecurityUtil;
import com.newland.wanxin.transaction.entity.Project;
import com.newland.wanxin.transaction.entity.Tender;
import com.newland.wanxin.transaction.mapper.ProjectMapper;
import com.newland.wanxin.transaction.mapper.TenderMapper;
import com.newland.wanxin.transaction.message.P2pTransactionProducer;
import com.newland.wanxin.transaction.model.LoginUser;
import com.newland.wanxin.transaction.service.ProjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.newland.wanxin.transaction.service.WanxinConfigService;
import com.newland.wanxin.utils.CodeNoUtil;
import com.newland.wanxin.utils.CommonUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p>
 * ??????????????? ???????????????
 * </p>
 *
 * @author leellun
 * @since 2022-11-22
 */
@Service
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements ProjectService {
    @Autowired
    private ConsumerApiAgent consumerApiAgent;

    @Autowired
    private DepositoryAgentApiAgent depositoryAgentApiAgent;

    @Autowired
    private ContentSearchApiAgent contentSearchApiAgent;

    @Autowired
    private WanxinConfigService configService;

    @Autowired
    private TenderMapper tenderMapper;

    @Autowired
    private P2pTransactionProducer p2pTransactionProducer;


    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        RestResponse<ConsumerDTO> restResponse = consumerApiAgent.getCurrConsumer(SecurityUtil.getUser().getMobile());
        projectDTO.setConsumerId(restResponse.getResult().getId());
        projectDTO.setUserNo(restResponse.getResult().getUserNo());
        // ??????????????????
        projectDTO.setProjectNo(CodeNoUtil.getNo(CodePrefixCode.CODE_PROJECT_PREFIX));
        // ??????????????????
        projectDTO.setProjectStatus(ProjectCode.COLLECTING.getCode());
        // ????????????????????????, ?????????
        projectDTO.setStatus(StatusCode.STATUS_OUT.getCode());
        // ????????????????????????
        projectDTO.setCreateDate(LocalDateTime.now());
        // ??????????????????
        projectDTO.setRepaymentWay(RepaymentWayCode.FIXED_REPAYMENT.getCode());
        // ??????????????????
        projectDTO.setType("NEW");
        Project project = convertProjectDTOToEntity(projectDTO);
        project.setBorrowerAnnualRate(configService.getBorrowerAnnualRate());
        project.setAnnualRate(configService.getAnnualRate());
        // ????????????(?????????????????????)
        project.setCommissionAnnualRate(configService.getCommissionAnnualRate());
        // ????????????
        project.setIsAssignment(0);
        // ??????????????????, ??????+??????+???N?????????
        // ????????????
        String sex = Integer.parseInt(restResponse.getResult().getIdNumber()
                .substring(16, 17)) % 2 == 0 ? "??????" : "??????";
        // ??????????????????????????????
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Project::getConsumerId,
                restResponse.getResult().getId());
        project.setName(restResponse.getResult().getFullname() + sex
                + "???" + (count(queryWrapper) + 1) + "?????????");
        save(project);

        projectDTO.setId(project.getId());
        projectDTO.setName(project.getName());
        return projectDTO;
    }

    @Override
    public PageVO<ProjectDTO> queryProjectsByQueryDTO(ProjectQueryDTO projectQueryDTO, String order, Integer pageNo, Integer pageSize, String sortBy) {

        //?????????
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        // ????????????
        if (StringUtils.isNotBlank(projectQueryDTO.getType())) {
            queryWrapper.lambda().eq(Project::getType, projectQueryDTO.getType());
        } // ??????????????????(?????????) -- ??????
        if (null != projectQueryDTO.getStartAnnualRate()) {
            queryWrapper.lambda().ge(Project::getAnnualRate,
                    projectQueryDTO.getStartAnnualRate());
        }
        if (null != projectQueryDTO.getEndAnnualRate()) {
            queryWrapper.lambda().le(Project::getAnnualRate,
                    projectQueryDTO.getStartAnnualRate());
        } // ???????????? -- ??????
        if (null != projectQueryDTO.getStartPeriod()) {
            queryWrapper.lambda().ge(Project::getPeriod,
                    projectQueryDTO.getStartPeriod());
        }
        if (null != projectQueryDTO.getEndPeriod()) {
            queryWrapper.lambda().le(Project::getPeriod,
                    projectQueryDTO.getEndPeriod());
        } // ????????????
        if (StringUtils.isNotBlank(projectQueryDTO.getProjectStatus())) {
            queryWrapper.lambda().eq(Project::getProjectStatus,
                    projectQueryDTO.getProjectStatus());
        }
        if (pageNo == null) {
            pageNo = 1;
        }
        if (pageSize == null) {
            pageSize = 20;
        }
        //??????
        // ??????????????????
        Page<Project> page = new Page<>(pageNo, pageSize);

        //??????
        if (StringUtils.isNotBlank(order) && StringUtils.isNotBlank(sortBy)) {
            if (order.toLowerCase().equals("asc")) {
                queryWrapper.orderByAsc(sortBy);
            } else if (order.toLowerCase().equals("desc")) {
                queryWrapper.orderByDesc(sortBy);
            }
        } else {
            queryWrapper.lambda().orderByDesc(Project::getCreateDate);
        }

        //????????????
        IPage<Project> iPage = page(page, queryWrapper);

        //????????????
        List<ProjectDTO> projectDTOList = convertProjectEntityListToDTOList(iPage.getRecords());
        return new PageVO<>(projectDTOList, iPage.getTotal(), pageNo, pageSize);

    }

    @Override
    public String projectsApprovalStatus(Long id, String approveStatus) {
        //1.??????id??????????????????????????????DTO??????
        Project project = getById(id);
        ProjectDTO projectDTO = convertProjectEntityToDTO(project);
        //2.???????????????(??????????????????)
        if (StringUtils.isBlank(project.getRequestNo())) {
            projectDTO.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
            update(Wrappers.<Project>lambdaUpdate().set(Project::getRequestNo,
                    projectDTO.getRequestNo()).eq(Project::getId, id));
        }

        //3.??????feign????????????????????????????????????????????????????????????
        RestResponse<String> restResponse = depositoryAgentApiAgent.createProject(projectDTO);

        if (DepositoryReturnCode.RETURN_CODE_00000.getCode()
                .equals(restResponse.getResult())) {
            //4.????????????????????????
            update(Wrappers.<Project>lambdaUpdate().set(Project::getStatus, Integer.parseInt(approveStatus)).eq(Project::getId, id));
            return "success";
        }

        //5.????????????????????????
        throw new BusinessException(TransactionErrorCode.E_150113);
    }

    private List<ProjectDTO> convertProjectEntityListToDTOList(java.util.List<Project>
                                                                       projectList) {
        if (projectList == null) {
            return null;
        }
        List<ProjectDTO> dtoList = new ArrayList<>();
        projectList.forEach(project -> {
            ProjectDTO projectDTO = new ProjectDTO();
            BeanUtils.copyProperties(project, projectDTO);
            dtoList.add(projectDTO);
        });
        return dtoList;
    }

    private Project convertProjectDTOToEntity(ProjectDTO projectDTO) {
        if (projectDTO == null) {
            return null;
        }
        Project project = new Project();
        BeanUtils.copyProperties(projectDTO, project);
        return project;
    }

    private ProjectDTO convertProjectEntityToDTO(Project project) {
        if (project == null) {
            return null;
        }
        ProjectDTO projectDTO = new ProjectDTO();
        BeanUtils.copyProperties(project, projectDTO);
        return projectDTO;
    }

    @Override
    public PageVO<ProjectDTO> queryProjects(ProjectQueryDTO projectQueryDTO,
                                            String order, Integer pageNo, Integer pageSize, String sortBy) {
        RestResponse<PageVO<ProjectDTO>> esResponse = contentSearchApiAgent.queryProjectIndex(projectQueryDTO, pageNo, pageSize, sortBy, order);
        if (!esResponse.isSuccessful()) {
            throw new BusinessException(CommonErrorCode.UNKOWN);
        }
        return esResponse.getResult();
    }

    @Override
    public List<ProjectDTO> queryProjectsIds(String ids) {
        //1. ??????????????????
        QueryWrapper<Project> queryWrapper = new QueryWrapper<>();
        List<Long> list = new ArrayList<>();
        Arrays.asList(ids.split(",")).forEach(str -> {
            list.add(Long.parseLong(str));
        });
        queryWrapper.lambda().in(Project::getId, list); // .... where  id  in  (1,2,3,4,5)
        List<Project> projects = list(queryWrapper);
        List<ProjectDTO> dtos = new ArrayList<>();
        //2.?????????DTO??????
        for (Project project : projects) {
            ProjectDTO projectDTO = convertProjectEntityToDTO(project);
            // 3. ??????????????????
            projectDTO.setRemainingAmount(getProjectRemainingAmount(project));
            //4. ??????????????????
            projectDTO.setTenderCount(tenderMapper.selectCount(Wrappers.<Tender>lambdaQuery().eq(Tender::getProjectId, project.getId())));
            dtos.add(projectDTO);
        }
        return dtos;

    }

    @Override
    public List<TenderOverviewDTO> queryTendersByProjectId(Long id) {
        List<Tender> tenderList = tenderMapper.selectList(Wrappers.<Tender>lambdaQuery().eq(Tender::getProjectId, id));
        List<TenderOverviewDTO> tenderOverviewDTOS = new ArrayList<>();
        tenderList.forEach(tender -> {
            TenderOverviewDTO tenderOverviewDTO = new TenderOverviewDTO();
            BeanUtils.copyProperties(tender, tenderOverviewDTO);
            tenderOverviewDTO.setConsumerUsername(CommonUtil.hiddenMobile(tenderOverviewDTO.getConsumerUsername()));
            tenderOverviewDTOS.add(tenderOverviewDTO);
        });
        return tenderOverviewDTOS;
    }

    @Override
    public TenderDTO createTender(ProjectInvestDTO projectInvestDTO) {
        //1.??????????????????
        //1.1 ????????????????????????????????????????????????
        BigDecimal amount = new BigDecimal(projectInvestDTO.getAmount());
        BigDecimal miniInvestmentAmount = configService.getMiniInvestmentAmount();
        if (amount.compareTo(miniInvestmentAmount) < 0) {
            throw new BusinessException(TransactionErrorCode.E_150109);
        }
        //1.2 ????????????????????????????????????
        LoginUser user = SecurityUtil.getUser();
        RestResponse<ConsumerDTO> restResponse = consumerApiAgent.getCurrConsumer(user.getMobile());
        RestResponse<BalanceDetailsDTO> balanceDetailsDTORestResponse = consumerApiAgent.getBalance(restResponse.getResult().getUserNo());
        BigDecimal myBalance = balanceDetailsDTORestResponse.getResult().getBalance();
        if (myBalance.compareTo(amount) < 0) {
            throw new BusinessException(TransactionErrorCode.E_150112);
        }

        //1.3 ??????????????????????????????????????????FULLY???????????????
        Project project = getById(projectInvestDTO.getId());
        if (project.getProjectStatus().equalsIgnoreCase(ProjectCode.FULLY.getCode())) {
            throw new BusinessException(TransactionErrorCode.E_150114);
        }

        //1.4 ????????????????????????????????????????????????
        BigDecimal remainingAmount = getProjectRemainingAmount(project);
        if (amount.compareTo(remainingAmount) < 1) {
            //1.5 ????????????????????????????????????????????????????????????????????????
            //??????????????????1???   ?????????????????????8???   ??????2???   ????????????1950???
            //????????????????????????????????????????????? = ???????????????????????? - ??????????????????
            BigDecimal subtract = remainingAmount.subtract(amount);
            int result = subtract.compareTo(configService.getMiniInvestmentAmount());
            if (result < 0) {
                if (subtract.compareTo(new BigDecimal("0.0")) != 0) {
                    throw new BusinessException(TransactionErrorCode.E_150111);
                }
            }

            //2. ????????????????????????????????????????????????
            //2.1 ??????????????????
            final Tender tender = new Tender();
            // ?????????????????????( ?????????????????? )
            tender.setAmount(amount);
            // ?????????????????????
            tender.setConsumerId(restResponse.getResult().getId());
            tender.setConsumerUsername(restResponse.getResult().getUsername());
            // ?????????????????????
            tender.setUserNo(restResponse.getResult().getUserNo());
            // ????????????
            tender.setProjectId(projectInvestDTO.getId());
            // ????????????
            tender.setProjectNo(project.getProjectNo());
            // ????????????
            tender.setTenderStatus(TradingCode.FROZEN.getCode());
            // ????????????
            tender.setCreateDate(LocalDateTime.now());
            // ???????????????
            tender.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));
            // ????????????
            tender.setStatus(0);
            tender.setProjectName(project.getName());
            // ????????????(??????:???)
            tender.setProjectPeriod(project.getPeriod());
            // ????????????(???????????????)
            tender.setProjectAnnualRate(project.getAnnualRate());
            // ??????????????????
            tenderMapper.insert(tender);

            //2.2 ?????????????????????????????????
            // ??????????????????
            UserAutoPreTransactionRequest userAutoPreTransactionRequest = new
                    UserAutoPreTransactionRequest();
            // ????????????
            userAutoPreTransactionRequest.setAmount(amount);
            // ?????????????????????
            userAutoPreTransactionRequest.setBizType(PreprocessBusinessTypeCode.TENDER.getCode());
            // ?????????
            userAutoPreTransactionRequest.setProjectNo(project.getProjectNo());
            // ???????????????
            userAutoPreTransactionRequest.setRequestNo(tender.getRequestNo());
            // ?????????????????????
            userAutoPreTransactionRequest.setUserNo(restResponse.getResult().getUserNo());
            // ?????? ????????????????????????
            userAutoPreTransactionRequest.setId(tender.getId());
            // ??????????????????????????????
            RestResponse<String> response = depositoryAgentApiAgent.userAutoPreTransaction(userAutoPreTransactionRequest);

            //3.??????????????????????????????
            //3.1 ????????????
            if (response.getResult().equals(DepositoryReturnCode.RETURN_CODE_00000.getCode())) {
                //3.2 ???????????????????????????
                tender.setStatus(1);
                tenderMapper.updateById(tender);
                //3.3 ??????????????????????????????????????????????????????????????????
                BigDecimal remainAmont = getProjectRemainingAmount(project);
                if (remainAmont.compareTo(new BigDecimal(0)) == 0) {
                    project.setProjectStatus(ProjectCode.FULLY.getCode());
                    updateById(project);
                }

                //3.4 ?????????DTO??????????????????????????????
                TenderDTO tenderDTO = convertTenderEntityToDTO(tender);
                // ??????????????????
                project.setRepaymentWay(RepaymentWayCode.FIXED_REPAYMENT.getDesc());
                tenderDTO.setProject(convertProjectEntityToDTO(project));
                // ??????????????????
                // ????????????????????????????????????
                final Double ceil = Math.ceil(project.getPeriod() / 30.0);
                Integer month = ceil.intValue();
                tenderDTO.setExpectedIncome(IncomeCalcUtil.getIncomeTotalInterest(new BigDecimal(projectInvestDTO.getAmount()), configService.getAnnualRate(), month));
                return tenderDTO;
            } else {
                throw new BusinessException(TransactionErrorCode.E_150113);
            }

        } else {
            throw new BusinessException(TransactionErrorCode.E_150110);
        }

    }

    @Override
    public String loansApprovalStatus(Long id, String approveStatus, String commission) {
        //???????????????1. ??????????????????
        // ????????????
        Project project = getById(id);
        // ????????????
        QueryWrapper<Tender> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(Tender::getProjectId, id);
        List<Tender> tenderList = tenderMapper.selectList(queryWrapper);
        // ??????????????????
        LoanRequest loanRequest = generateLoanRequest(project, tenderList, commission);

        //???????????????2. ??????
        RestResponse<String> restResponse = depositoryAgentApiAgent.confirmLoan(loanRequest);
        if (restResponse.getResult().equals(DepositoryReturnCode.RETURN_CODE_00000.getCode())) {
            updateTenderStatusAlreadyLoan(tenderList);
            //????????????3. ????????????
            //????????????????????????
            ModifyProjectStatusDTO modifyProjectStatusDTO = new ModifyProjectStatusDTO();
            modifyProjectStatusDTO.setId(project.getId());
            modifyProjectStatusDTO.setProjectStatus(ProjectCode.REPAYING.getCode());
            modifyProjectStatusDTO.setRequestNo(loanRequest.getRequestNo());
            modifyProjectStatusDTO.setProjectNo(project.getProjectNo());

            //?????????????????????????????????
            RestResponse<String> modifyProjectStatus = depositoryAgentApiAgent.modifyProjectStatus(modifyProjectStatusDTO);
            if (modifyProjectStatus.getResult().equals(DepositoryReturnCode.RETURN_CODE_00000.getCode())) {
                //4. ????????????
                //????????????
                ProjectWithTendersDTO projectWithTendersDTO = new ProjectWithTendersDTO();
                //1.????????????
                projectWithTendersDTO.setProject(convertProjectEntityToDTO(project));
                //2.????????????
                projectWithTendersDTO.setTenders(convertTenderEntityListToDTOList(tenderList));
                //3.???????????????
                projectWithTendersDTO.setCommissionInvestorAnnualRate(configService.getCommissionInvestorAnnualRate());
                //4.???????????????
                projectWithTendersDTO.setCommissionBorrowerAnnualRate(configService.getBorrowerAnnualRate());

                //????????????????????????  ??????RocketMQ
                p2pTransactionProducer.updateProjectStatusAndStartRepayment(project, projectWithTendersDTO);

                return "????????????";

            } else {
                throw new BusinessException(TransactionErrorCode.E_150113);
            }

        } else {
            throw new BusinessException(TransactionErrorCode.E_150113);
        }


    }

    @Transactional(rollbackFor = BusinessException.class)
    @Override
    public Boolean updateProjectStatusAndStartRepayment(Project project) {
        //??????????????????????????????????????????????????????
        project.setProjectStatus(ProjectCode.REPAYING.getCode());
        return updateById(project);
    }

    /**
     * ??????????????????????????????????????????
     */
    private void updateTenderStatusAlreadyLoan(List<Tender> tenderList) {
        tenderList.forEach(tender -> {
            tender.setTenderStatus(TradingCode.LOAN.getCode());
            tenderMapper.updateById(tender);
        });
    }

    /**
     * ?????????????????????????????????????????????
     */
    public LoanRequest generateLoanRequest(Project project, List<Tender> tenderList, String commission) {
        LoanRequest loanRequest = new LoanRequest();

        //????????????id
        loanRequest.setId(project.getId());

        //??????????????????
        if (StringUtils.isNotBlank(commission)) {
            loanRequest.setCommission(new BigDecimal(commission));
        }

        //??????????????????
        loanRequest.setProjectNo(project.getProjectNo());

        //?????????????????????
        loanRequest.setRequestNo(CodeNoUtil.getNo(CodePrefixCode.CODE_REQUEST_PREFIX));

        List<LoanDetailRequest> details = new ArrayList<>();
        tenderList.forEach(tender -> {
            LoanDetailRequest loanDetailRequest = new LoanDetailRequest();
            loanDetailRequest.setAmount(tender.getAmount());
            loanDetailRequest.setPreRequestNo(tender.getRequestNo());
            details.add(loanDetailRequest);
        });

        loanRequest.setDetails(details);

        return loanRequest;

    }

    private List<TenderDTO> convertTenderEntityListToDTOList(List<Tender> records) {
        if (records == null) {
            return null;
        }
        List<TenderDTO> dtoList = new ArrayList<>();
        records.forEach(tender -> {
            TenderDTO tenderDTO = new TenderDTO();
            BeanUtils.copyProperties(tender, tenderDTO);
            dtoList.add(tenderDTO);
        });
        return dtoList;
    }

    private TenderDTO convertTenderEntityToDTO(Tender tender) {
        if (tender == null) {
            return null;
        }
        TenderDTO tenderDTO = new TenderDTO();
        BeanUtils.copyProperties(tender, tenderDTO);
        return tenderDTO;
    }

    /**
     * * ??????????????????????????????
     *
     * @param project
     * @return
     */
    private BigDecimal getProjectRemainingAmount(Project project) {
        // ????????????id??????????????????????????????
        List<BigDecimal> decimalList =
                tenderMapper.selectAmountInvestedByProjectId(project.getId());
        // ???????????????
        BigDecimal amountInvested = new BigDecimal("0.0");
        for (BigDecimal d : decimalList) {
            amountInvested = amountInvested.add(d);
        }
        // ??????????????????
        return project.getAmount().subtract(amountInvested);
    }
}
