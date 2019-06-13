package com.cqns.demo.web.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.IOUtils;
import com.cqns.demo.dao.entity.*;
import com.cqns.demo.dao.repository.*;
import com.cqns.demo.utils.JwtTokenUtil;
import com.cqns.demo.utils.ResultInfo;
import com.cqns.demo.web.service.baseservice.RoleService;
import com.cqns.demo.web.service.baseservice.UserRoleService;
import com.cqns.demo.web.service.baseservice.UserService;
import com.cqns.demo.web.service.eventservice.EventService;
import com.cqns.demo.web.service.eventservice.HandleEventDetailsService;
import com.cqns.demo.web.vo.EventVo;
import com.cqns.demo.web.vo.HandleEventDetailsVo;
import com.cqns.demo.web.vo.RoleVo;
import com.cqns.demo.web.vo.UserRoleVo;
import com.google.common.base.Preconditions;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author BryanChan
 * @Date 2019/5/22 16:39
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/Event")
public class EventController {

    private static Logger logger = LoggerFactory.getLogger(EventController.class);

    private final String uploadPath = "/Users/bryanchan/uploadFile";
    @Resource
    private EventService eventService;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private HistoryService historyService;
    @Resource
    private EventAttachmentRepository eventAttachmentRepository;
    @Resource
    private UserRoleService userRoleService;
    @Resource
    private RoleService roleService;
    @Resource
    private DictionaryRepository dictionaryRepository;
    @Resource
    private UserRoleRepository userRoleRepository;
    @Resource
    private UserService userService;
    @Resource
    private HandleEventDetailsService handleEventDetailsService;
    @Resource
    private HandleEventDetailsRepository handleEventDetailsRepository;
    private final String string = "/uploadAttachmentFile";

    @RequestMapping(value = "/queryInstitution", method = RequestMethod.GET)
    public ResultInfo<List> queryInstitution() {

        UserRoleVo userRoleVo = new UserRoleVo();

        userRoleVo.setUserName(JwtTokenUtil.getUser().getUsername());

        return ResultInfo.create(List.class).success(this.userRoleService.userRoleVoList(userRoleVo)
                .stream().map(UserRole::getRoleId).collect(Collectors.toList())
                .stream().map(this.roleService::selectById).collect(Collectors.toList()));
    }

    @RequestMapping(value = "/eventVoPageInfo", method = RequestMethod.POST)
    public ResultInfo<Map> eventVoPageInfo(@RequestBody EventVo eventVo) {
        return ResultInfo.create(Map.class).success(this.eventService.eventVoPageInfo(eventVo));
    }

    @RequestMapping(value = "/editEvent", method = RequestMethod.POST)
    public ResultInfo<Void> editEvent(@RequestBody Event event) {

        event.setUpdateBy(JwtTokenUtil.getUser().getUsername());

        return this.eventService.insert(event) ?
                ResultInfo.create().success().setMsg("修改成功") :
                ResultInfo.create().fail("修改失败");
    }

    @RequestMapping(value = "/saveEvent", method = RequestMethod.POST)
    public ResultInfo<Void> saveEvent(@RequestBody Event event) {

        event.setCreatedBy(JwtTokenUtil.getUser().getUsername());

        event.setUpdateBy(JwtTokenUtil.getUser().getUsername());

        event.setStatus("save");

        return this.eventService.insert(event) ?
                ResultInfo.create().success().setMsg("保存成功") :
                ResultInfo.create().fail("保存失败");
    }

    @RequestMapping(value = "/submitEvent", method = RequestMethod.POST)
    public void submitEvent(@RequestBody Event event) {

        Preconditions.checkNotNull(event.getId(), "事件Id不能为空");

        UserDetails userDetails = JwtTokenUtil.getUser();

        Map<String, Object> vars = Maps.newHashMap();

        vars.put("submitPerson", userDetails.getUsername());

        vars.put("handleEventStaff", event.getHandleEventStaff());

        event.setStatus("submit");

        this.eventService.updateById(event);

        //提交问题之前先启动activiti流程
        runtimeService.startProcessInstanceByKey("issueProcess", String.valueOf(event.getId()), vars);

        List<Task> tasks = taskService.createTaskQuery().
                taskCandidateOrAssigned(userDetails.getUsername()).list();

        for (Task task : tasks) {

            taskService.complete(task.getId());

        }
    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public List<Optional<EventVo>> find() {

        UserDetails userDetails = JwtTokenUtil.getUser();

        Preconditions.checkNotNull(userDetails.getUsername(), "用户名不能为空");

        List<Task> tasks = taskService.createTaskQuery().taskAssignee(userDetails.getUsername()).list();

        List<Event> events = Lists.newArrayList();

        for (Task task : tasks) {

            String processInstanceId = taskService.createTaskQuery().taskId(task.getId()).singleResult().getProcessInstanceId();

            ProcessInstance processInstance = runtimeService.createProcessInstanceQuery().processInstanceId(processInstanceId).orderByProcessInstanceId().desc().singleResult();

            //BusinessKey对应启动流程的时候放进去的key,也就是我们的eventId
            String businessKey = processInstance.getBusinessKey();

            if (Strings.isNullOrEmpty(businessKey)) {

                continue;

            }

            Optional optional = eventService.selectById(Long.parseLong(businessKey));

            if (optional.isPresent()) {

                events.add((Event) optional.get());

            }

        }

        List<Optional<EventVo>> eventVos = JSON.parseObject(JSON.toJSONString(events), new TypeReference<List<Optional<EventVo>>>() {
        }.getType());

        eventVos.forEach(Optional -> Optional.get().setShowHandleEventGroup(this.roleService.selectById(Optional.get().getHandleEventGroup()).get().getName()));

        eventVos.forEach(Optional -> Optional.get().setShowHandleEventStaff(this.userService.queryUserByName(Optional.get().getHandleEventStaff()).getDisplayName()));

        eventVos.forEach(Optional -> Optional.get().setShowPriorityLevel(this.dictionaryRepository.findDictionaryByKey(Optional.get().getPriorityLevel()).getName()));

        eventVos.forEach(Optional -> Optional.get().setShowEventType(this.dictionaryRepository.findDictionaryByKey(Optional.get().getEventType()).getName()));

        eventVos.forEach(Optional -> Optional.get().setShowInstitution(this.roleService.selectById(Optional.get().getInstitution()).get().getName()));

        return eventVos;
    }

    @RequestMapping(value = "/closeProcess", method = RequestMethod.POST)
    public ResultInfo<Void> closeProcess(@RequestBody Event event) {
        try{

            event.setStatus("close");

            this.eventService.updateById(event);

            ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(String.valueOf(event.getId())).singleResult();

            this.runtimeService.deleteProcessInstance(processInstance.getProcessInstanceId(),"流程被事件提出者关闭");

            return ResultInfo.create().success().setMsg("关闭成功");

        }catch (Exception e) {

            logger.error("Error", e);

            return ResultInfo.create().success().setMsg("关闭失败");
        }

    }

    @RequestMapping(value = "/approve", method = RequestMethod.GET)
    public void approve(String code, String businessId) {

        UserDetails userDetails = JwtTokenUtil.getUser();

        Preconditions.checkNotNull(userDetails.getUsername(), "用户名不能为空");

        List<Task> tasks = taskService.createTaskQuery().taskCandidateOrAssigned(userDetails.getUsername()).processInstanceBusinessKey(businessId).list();

        Map<String, Object> vars = Maps.newHashMap();

        for (Task task : tasks) {

            vars.put("code", code);
            vars.put("handleEventStaff", "kjyw");

            taskService.complete(task.getId(), vars);

        }

    }

    @RequestMapping(value = "/uploadAttachmentFile", method = RequestMethod.POST)
    public void uploadAttachmentFile(@RequestBody MultipartFile file, String id) throws IOException {

        try {
            File fileUpload = new File(this.uploadPath + "/" + file.getOriginalFilename());

            file.transferTo(fileUpload);

            EventAttachment eventAttachment = new EventAttachment();

            eventAttachment.setEventIdentifier(id);

            eventAttachment.setAttachmentName(file.getOriginalFilename());

            eventAttachment.setAttachmentPath(fileUpload.getAbsolutePath());

            eventAttachment.setCreatedBy(JwtTokenUtil.getUser().getUsername());

            this.eventAttachmentRepository.save(eventAttachment);

        } catch (Exception e) {

            logger.error("Error", e);

        }

    }

    @RequestMapping(value = "/getUploadFileList", method = RequestMethod.GET)
    public ResultInfo<List> getUploadFileList(String eventIdentifier, String createdBy) {

        return ResultInfo.create(List.class).success(this.eventService.getUploadFileList(eventIdentifier, createdBy));

    }

    @RequestMapping(value = "/deleteEvent", method = RequestMethod.GET)
    public void deleteEvent(long id, String eventIdentifier) {

        Preconditions.checkNotNull(id, "事件ID不能为空");

        Preconditions.checkNotNull(eventIdentifier, "事件编码不能为空");
        //通过ID先把问题删除
        this.eventService.deleteById(id);
        //通过事件编码查出所有的附件，将问题关联的附件删除
        List<EventAttachment> eventAttachments = this.eventAttachmentRepository.findByEventIdentifier(eventIdentifier);

        for (EventAttachment eventAttachment : eventAttachments) {

            File deleteFile = new File(eventAttachment.getAttachmentPath());

            if (deleteFile.isFile() && deleteFile.exists()) {

                deleteFile.delete();

            }
        }

        this.eventAttachmentRepository.deleteEventAttachmentByEventIdentifier(eventIdentifier);

    }

    @RequestMapping(value = "/deleteUploadFile", method = RequestMethod.GET)
    public void deleteUploadFile(String fileUrl, long id) {

        File deleteFile = new File(fileUrl);

        this.eventAttachmentRepository.deleteById(id);

        if (deleteFile.isFile() && deleteFile.exists()) {

            deleteFile.delete();

        }

    }

    @RequestMapping(value = "/downloadAttachmentFile", method = RequestMethod.GET)
    public void downloadAttachmentFile(String fileUrl, HttpServletResponse response) throws IOException {

        String fileName;

        if (fileUrl.contains("/")) {

            fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);

        } else {

            fileName = fileUrl;

        }

        response.setContentType("application/octet-stream;charset=utf-8");

        response.addHeader("Content-Disposition", " attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));

        FileInputStream fis = null;

        BufferedInputStream bis = null;

        try {
            byte[] buffer = new byte[1024];

            fis = new FileInputStream(fileUrl);

            bis = new BufferedInputStream(fis);

            OutputStream os = response.getOutputStream();

            int i = bis.read(buffer);

            while (i != -1) {

                os.write(buffer, 0, i);

                i = bis.read(buffer);
            }

            os.flush();
        } finally {

            IOUtils.close(fis);

            IOUtils.close(bis);
        }
    }

    @RequestMapping(value = "/queryDictionary", method = RequestMethod.GET)
    public ResultInfo<List> queryDictionary(String key) {
        return ResultInfo.create(List.class)
                .success(this.dictionaryRepository.findDictionaryByParentId(this.dictionaryRepository.findDictionaryByKey(key).getId()));
    }

//    @RequestMapping(value = "/queryHandleEventGroup", method = RequestMethod.GET)
//    public ResultInfo<List> queryHandleEventGroup(Long institutionId, String rank) {
//
//        List<RoleVo> handleEventGroups = Lists.newArrayList();
//
//        if (Objects.nonNull(institutionId)) {
//
//            //事件创建人选了提出事件机构，查询的上级别处理部门
//            Role role = this.roleService.selectById(this.roleService.selectById(institutionId).get().getParentId()).get();
//
//            RoleVo roleVo = new RoleVo();
//
//            BeanUtils.copyProperties(role, roleVo);
//
//            handleEventGroups.add(roleVo);
//
//        } else {
//
//            if ("same".equalsIgnoreCase(rank)) {
//                //转办查询同级别
//                List<RoleVo> role = this.roleService.queryRolesByUserName(JwtTokenUtil.getUser().getUsername());
//
//                handleEventGroups.addAll(role);
//            }
//
//            if ("parent".equalsIgnoreCase(rank)) {
//
//                List<RoleVo> role = this.roleService.queryRolesByUserName(JwtTokenUtil.getUser().getUsername());
//
//                //移交上级查询上级别部门
//                Role role1 = this.roleService.selectById(role.get(0).getParentId()).get();
//
//                RoleVo roleVo = new RoleVo();
//
//                BeanUtils.copyProperties(role1, roleVo);
//
//                handleEventGroups.add(roleVo);
//
//            }
//
//        }
//
//        return ResultInfo.create(List.class)
//                .success(handleEventGroups);
//    }

    @RequestMapping(value = "/queryHandleEventStaff", method = RequestMethod.GET)
    public ResultInfo<List> queryHandleEventStaff(Long handleEventGroupId) {

        List<Optional> users = this.userRoleRepository.findByRoleId(this.roleService.selectById(handleEventGroupId).get().getId())
                .stream().map(UserRole::getUserId).collect(Collectors.toList())
                .stream().map(this.userService::selectById).collect(Collectors.toList());
        return ResultInfo.create(List.class)
                .success(users);
    }

//    @RequestMapping(value = "/getCurrentOptionResult", method = RequestMethod.GET)
//    public ResultInfo<Map> getCurrentOptionResult() {
//
//        Map<String, Object> optionResult = Maps.newHashMap();
//
//        //选出登陆者的所有上级部门（角色）并且过滤掉顶级部门（角色）
//        List<Optional<Role>> roles = this.roleService.queryRolesByUserName(JwtTokenUtil.getUser().getUsername())
//                .stream().map(Role::getParentId).map(this.roleService::selectById).collect(Collectors.toList())
//                .stream().filter(Optional -> Optional.isPresent()).collect(Collectors.toList());
//
//        List<Long> roleIds = roles.stream().filter(Optional::isPresent).map(Optional::get).collect(Collectors.toList())
//                .stream().map(Role::getId).collect(Collectors.toList());
//
//        List<Optional> users = Lists.newArrayList();
//
//        for (Long id : roleIds) {
//
//            users.addAll(this.userRoleRepository.findByRoleId(id)
//                    .stream().map(UserRole::getUserId)
//                    .map(this.userService::selectById).collect(Collectors.toList()));
//
//        }
//
//        optionResult.put("handleEventGroups", roles);
//
//        optionResult.put("handleEventStaffs", users);
//
//        return ResultInfo.create(Map.class)
//                .success(optionResult);
//    }

    @RequestMapping(value = "/getHandleEventHistoryRecord", method = RequestMethod.GET)
    public ResultInfo<List> getHandleEventHistoryRecord() {

        //根据历史service查询处对应的事件ID也就是说我们的businessKey
        List<HistoricTaskInstance> historicTaskInstanceList = historyService.createHistoricTaskInstanceQuery()
                .taskAssignee(JwtTokenUtil.getUser().getUsername()).list();

        List<Long> historyEventIds = Lists.newArrayList();

        for (HistoricTaskInstance historicTaskInstance : historicTaskInstanceList) {

            HistoricProcessInstance historicProcessInstance = historyService.createHistoricProcessInstanceQuery()
                    .processInstanceId(historicTaskInstance.getProcessInstanceId())
                    .orderByProcessInstanceId().desc().singleResult();

            if (!Strings.isNullOrEmpty(historicProcessInstance.getBusinessKey())) {

                historyEventIds.add(Long.valueOf(historicProcessInstance.getBusinessKey()));

            }

        }

        List<Optional<Event>> events = historyEventIds.stream().distinct()
                .map(this.eventService::selectById)
                .filter(Optional::isPresent).collect(Collectors.toList());

        List<Optional<EventVo>> eventVos = JSON.parseObject(JSON.toJSONString(events), new TypeReference<List<Optional<EventVo>>>() {
        }.getType());

        eventVos.forEach(Optional -> Optional.get().setShowHandleEventGroup(this.roleService.selectById(Optional.get().getHandleEventGroup()).get().getName()));

        eventVos.forEach(Optional -> Optional.get().setShowHandleEventStaff(this.userService.queryUserByName(Optional.get().getHandleEventStaff()).getDisplayName()));

        eventVos.forEach(Optional -> Optional.get().setShowPriorityLevel(this.dictionaryRepository.findDictionaryByKey(Optional.get().getPriorityLevel()).getName()));

        return ResultInfo.create(List.class)
                .success(eventVos);
    }

    @RequestMapping(value = "/saveDetail", method = RequestMethod.POST)
    public ResultInfo<Void> saveDetail(@RequestBody HandleEventDetails handleEventDetails) {

        handleEventDetails.setCreatedBy(JwtTokenUtil.getUser().getUsername());

        return this.handleEventDetailsService.insert(handleEventDetails) ?
                ResultInfo.create().success().setMsg("保存成功") :
                ResultInfo.create().fail("保存失败");
    }

    @RequestMapping(value = "/queryHandleEventDetails", method = RequestMethod.GET)
    public ResultInfo<List> queryHandleEventDetails(String eventIdentifier, String handleEventStaff) {


        if (Strings.isNullOrEmpty(handleEventStaff)) {

            List<HandleEventDetails> handleEventDetails = this.handleEventDetailsRepository.findByEventIdentifierAndCreatedBy(eventIdentifier,JwtTokenUtil.getUser().getUsername());

            return ResultInfo.create(List.class)
                    .success(handleEventDetails);
        } else {

            List<HandleEventDetails> handleEventDetails = this.handleEventDetailsRepository.findByEventIdentifier(eventIdentifier);

            List<HandleEventDetailsVo> handleEventDetailsVos =JSON.parseObject(JSON.toJSONString(handleEventDetails), new TypeReference<List<HandleEventDetailsVo>>(){}.getType());

            handleEventDetailsVos.forEach(handleEventDetailsVo -> handleEventDetailsVo.setShowCreatedBy(this.userService.queryUserByName(handleEventDetailsVo.getCreatedBy()).getDisplayName()));

            return ResultInfo.create(List.class)
                    .success(handleEventDetailsVos);
        }

    }

    @RequestMapping(value = "/queryHandleEventDetailsById", method = RequestMethod.GET)
    public ResultInfo<HandleEventDetailsVo> queryHandleEventDetailsById(Long id) {

        HandleEventDetails handleEventDetails = this.handleEventDetailsService.selectById(id).get();

        HandleEventDetailsVo handleEventDetailsVo = new HandleEventDetailsVo();

        BeanUtils.copyProperties(handleEventDetails, handleEventDetailsVo);

        handleEventDetailsVo.setShowDemand(this.dictionaryRepository.findDictionaryByKey(handleEventDetailsVo.getDemand()).getName());

        handleEventDetailsVo.setShowHandleCategories(this.dictionaryRepository.findDictionaryByKey(handleEventDetailsVo.getHandleCategories()).getName());

        handleEventDetailsVo.setShowOptimizeCategories(this.dictionaryRepository.findDictionaryByKey(handleEventDetailsVo.getOptimizeCategories()).getName());

        if (this.roleService.selectById(handleEventDetailsVo.getHandleEventGroup()).isPresent())

            handleEventDetailsVo.setShowHandleEventGroup(this.roleService.selectById(handleEventDetailsVo.getHandleEventGroup()).get().getName());

        if (!Strings.isNullOrEmpty(handleEventDetailsVo.getHandleEventStaff())) {

            if (this.userService.selectById(Long.valueOf(handleEventDetailsVo.getHandleEventStaff())).isPresent())

                handleEventDetailsVo.setShowHandleEventStaff(this.userService.selectById(Long.valueOf(handleEventDetailsVo.getHandleEventStaff())).get().getDisplayName());

        }
//        handleEventDetailsVo.setShowSystem(this.dictionaryRepository.findDictionaryByKey(String.valueOf(handleEventDetailsVo.getSystem())).getName());

        handleEventDetailsVo.setShowTestCover(this.dictionaryRepository.findDictionaryByKey(handleEventDetailsVo.getTestCover()).getName());

        return ResultInfo.create(HandleEventDetailsVo.class)
                .success(handleEventDetailsVo);
    }

}
