package com.cqns.demo.web.controller;

import com.cqns.demo.dao.entity.Issue;
import com.cqns.demo.dao.entity.IssueAttachment;
import com.cqns.demo.dao.repository.IssueAttachmentRepository;
import com.cqns.demo.utils.JwtTokenUtil;
import com.cqns.demo.utils.ResultInfo;
import com.cqns.demo.web.service.issueservice.IssueService;
import com.cqns.demo.web.vo.IssueVo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * @Author BryanChan
 * @Date 2019/5/22 16:39
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/Issue")
public class IssueController {
    private static Logger logger = LoggerFactory.getLogger(IssueController.class);
    private final String uploadPath = "D:\\test";
    @Resource
    private IssueService issueService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private IssueAttachmentRepository issueAttachmentRepository;

    @RequestMapping(value = "/issueVoPageInfo", method = RequestMethod.POST)
    public ResultInfo<Page> issueVoPageInfo(@RequestBody IssueVo issueVo){
        return ResultInfo.create(Page.class).success(this.issueService.issueVoPageInfo(issueVo));
    }
    @RequestMapping(value = "/saveIssue", method = RequestMethod.POST)
    public ResultInfo<Void> saveIssue(@RequestBody Issue issue){
        issue.setCreatedBy(JwtTokenUtil.getUser().getUsername());
        issue.setUpdateBy(JwtTokenUtil.getUser().getUsername());
        return this.issueService.insert(issue) ?
                ResultInfo.create().success().setMsg("保存成功") :
                ResultInfo.create().fail("保存失败");
    }

    @RequestMapping(value="/submitIssue", method = RequestMethod.POST)
    public void submitIssue(@RequestBody Issue issue){

        Preconditions.checkNotNull(issue.getId(), "问题Id不能为空");

        UserDetails userDetails = JwtTokenUtil.getUser();

        Map<String, Object> vars = Maps.newHashMap();

        vars.put("submitPerson", userDetails.getUsername());

        //提交问题之前先启动activiti流程
        runtimeService.startProcessInstanceByKey("issueProcess", String.valueOf(issue.getId()), vars);

        List<Task> tasks = taskService.createTaskQuery().
                taskCandidateOrAssigned(userDetails.getUsername()).list();

        for (Task task : tasks) {

            taskService.complete(task.getId());

        }
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public List<Issue> find() {

        UserDetails userDetails = JwtTokenUtil.getUser();

        Preconditions.checkNotNull(userDetails.getUsername(), "用户名不能为空");

        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(userDetails.getUsername()).list();

        List<Issue> issues = Lists.newArrayList();

        for (Task task : tasks) {

            String processInstanceId = taskService.createTaskQuery().taskId(task.getId()).singleResult().getProcessInstanceId();

            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).orderByProcessInstanceId().desc().singleResult();

            //BusinessKey对应启动流程的时候放进去的key,也就是我们的issueId
            String businessKey = processInstance.getBusinessKey();

            if (Strings.isNullOrEmpty(businessKey)) {

                continue;

            }

            Optional optional = issueService.selectById(Long.parseLong(businessKey));

            if (optional.isPresent()) {

                issues.add((Issue) optional.get());

            }

        }

        return issues;
    }

    @RequestMapping(value = "/approve", method = RequestMethod.GET)
    public void approve(String code, String businessId){

        UserDetails userDetails = JwtTokenUtil.getUser();

        Preconditions.checkNotNull(userDetails.getUsername(), "用户名不能为空");

        List<Task> tasks = taskService.createTaskQuery().taskCandidateUser(userDetails.getUsername()).processInstanceBusinessKey(businessId).list();

        Map<String, Object> vars = Maps.newHashMap();

        for (Task task : tasks) {

                vars.put("code", code);

                taskService.complete(task.getId(),vars);

        }

    }

    @RequestMapping(value = "/uploadAttechmentFile", method = RequestMethod.POST)
    public void uploadAttechmentFile(@RequestBody MultipartFile file, String id) throws IOException{

        try {
            File fileUpload = new File(this.uploadPath + "/" + file.getOriginalFilename());

            file.transferTo(fileUpload);

            IssueAttachment issueAttachment = new IssueAttachment();

            issueAttachment.setIssueIdentifier(id);

            issueAttachment.setAttachmentName(file.getOriginalFilename());

            issueAttachment.setAttachmentPath(fileUpload.getAbsolutePath());

            issueAttachment.setCreatedBy(JwtTokenUtil.getUser().getUsername());

            issueAttachment.setUpdateBy(JwtTokenUtil.getUser().getUsername());

            this.issueAttachmentRepository.save(issueAttachment);

        } catch (Exception e) {

            logger.error("Error", e);

        }

    }

}
