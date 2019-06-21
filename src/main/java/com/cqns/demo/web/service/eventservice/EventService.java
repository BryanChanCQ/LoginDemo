package com.cqns.demo.web.service.eventservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.alibaba.fastjson.util.IOUtils;
import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.*;
import com.cqns.demo.dao.mapper.EventMapper;
import com.cqns.demo.dao.repository.*;
import com.cqns.demo.utils.JwtTokenUtil;
import com.cqns.demo.utils.ResultInfo;
import com.cqns.demo.utils.statusEnum;
import com.cqns.demo.web.service.AbstractCommonService;
import com.cqns.demo.web.service.baseservice.RoleService;
import com.cqns.demo.web.service.baseservice.UserService;
import com.cqns.demo.web.vo.BranchInfoVo;
import com.cqns.demo.web.vo.EventVo;
import com.cqns.demo.web.vo.HandleEventDetailsVo;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.io.*;
import java.net.URLEncoder;
import java.util.*;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;
import javax.servlet.http.HttpServletResponse;

/**
 * @Author BryanChan
 * @Date 2019/5/22 16:53
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Service
public class EventService extends AbstractCommonService<Event>{

    private static Logger logger = LoggerFactory.getLogger(EventService.class);
    @Resource
    private EventRepository eventRepository;
    @Resource
    private UserService userService;
    @Resource
    private DictionaryRepository dictionaryRepository;
    @Resource
    private EventAttachmentRepository eventAttachmentRepository;
    @Resource
    private BranchInfoRepository branchInfoRepository;
    @Resource
    private RuntimeService runtimeService;
    @Resource
    private TaskService taskService;
    @Resource
    private UserRepository userRepository;
    @Resource
    private HistoryService historyService;
    @Resource
    private HandleEventDetailsRepository handleEventDetailsRepository;
    @Resource
    private HandleEventDetailsService handleEventDetailsService;
    @Resource
    private EventMapper eventMapper;



    private final String uploadPath = "/Users/bryanchan/uploadFile";

    public Map<String, Object> eventVoPageInfo(EventVo eventVo){

        Map<String, Object> resultMap = Maps.newHashMap();

        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(JwtTokenUtil.getUser().getUsername())){

                predicates.add(criteriaBuilder.like(root.get("createdBy"),"%" + JwtTokenUtil.getUser().getUsername() + "%"));

            }

            if (!Strings.isNullOrEmpty(eventVo.getEventTitle())){

                predicates.add(criteriaBuilder.like(root.get("eventTitle"),"%" + eventVo.getEventTitle() + "%"));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        Pageable pageable = PageRequest.of(eventVo.getPage(), eventVo.getPageSize(), Sort.Direction.ASC, "rawUpdateTime");

        Page<Event> page = this.eventRepository.findAll(specification,pageable);

        List<EventVo> eventVos = Lists.newArrayList();

        for (Event event : page) {

            EventVo eventVo1 = new EventVo();

            BeanUtils.copyProperties(event, eventVo1);

            eventVo1.setShowInstitution(this.branchInfoRepository.findByBranCode(String.valueOf(event.getInstitution())).getBranName());

            eventVo1.setShowHandleEventGroup(this.branchInfoRepository.findByBranCode(String.valueOf(event.getHandleEventGroup())).getBranName());

            eventVo1.setShowHandleEventStaff(this.userService.queryUserByName(event.getHandleEventStaff()).getDisplayName());

            eventVo1.setShowPriorityLevel(this.dictionaryRepository.findDictionaryByKey(event.getPriorityLevel()).getName());

            eventVo1.setShowStatus(statusEnum.getEnumByKey(event.getStatus()).getValue());

            eventVos.add(eventVo1);
        }

        resultMap.put("ListEvent", eventVos);

        resultMap.put("page",page);

        return resultMap;
    }

    public List<EventAttachment> getUploadFileList(String eventIdentifier, String createdBy) {

        return !Strings.isNullOrEmpty(createdBy) ? this.eventAttachmentRepository.findByEventIdentifierAndAndCreatedBy(eventIdentifier, createdBy)
                : this.eventAttachmentRepository.findByEventIdentifierAndAndCreatedBy(eventIdentifier, JwtTokenUtil.getUser().getUsername());

    }

    public void submitEvent(Event event) {

        UserDetails userDetails = JwtTokenUtil.getUser();

        Map<String, Object> vars = Maps.newHashMap();

        vars.put("submitPerson", userDetails.getUsername());

        vars.put("handleEventStaff", event.getHandleEventStaff());

        event.setStatus(statusEnum.submit.getKey());
        try {

            this.updateById(event);

            //提交问题之前先启动activiti流程
            runtimeService.startProcessInstanceByKey("eventProcess", String.valueOf(event.getId()), vars);

            Task task = taskService.createTaskQuery().
                    taskCandidateOrAssigned(userDetails.getUsername()).processInstanceBusinessKey(String.valueOf(event.getId())).singleResult();

            taskService.complete(task.getId());
        } catch (Exception e) {

            logger.error("Error", e);

            throw new RuntimeException(e.getMessage());
        }

    }

    public PageInfo<EventVo> findDistributeToMe(HashMap map) {

        map.put("assignee", JwtTokenUtil.getUser().getUsername());

        PageHelper.startPage((int)map.get("pageNum"), (int)map.get("pageSize"));

        List<Event> events = this.eventMapper.findDistributeToMe(map);

        PageInfo<Event> pageInfo = new PageInfo<>(events);

        PageInfo<EventVo> pages = JSON.parseObject(JSON.toJSONString(pageInfo), new TypeReference<PageInfo<EventVo>>() {
        }.getType());

        for (EventVo eventVo1 : pages.getList()) {

            eventVo1.setShowHandleEventGroup(this.branchInfoRepository.findByBranCode(eventVo1.getHandleEventGroup()).getBranName());

            eventVo1.setShowHandleEventStaff(this.userService.queryUserByName(eventVo1.getHandleEventStaff()).getDisplayName());

            eventVo1.setShowPriorityLevel(this.dictionaryRepository.findDictionaryByKey(eventVo1.getPriorityLevel()).getName());

            eventVo1.setShowEventType(this.dictionaryRepository.findDictionaryByKey(eventVo1.getEventType()).getName());

            eventVo1.setShowInstitution(this.branchInfoRepository.findByBranCode(eventVo1.getInstitution()).getBranName());

            eventVo1.setShowStatus(statusEnum.getEnumByKey(eventVo1.getStatus()).getValue());
        }

        return pages;
    }

    public void closeProcess(Event event) {

        ProcessInstance processInstance = this.runtimeService.createProcessInstanceQuery().processInstanceBusinessKey(String.valueOf(event.getId())).singleResult();

        this.runtimeService.deleteProcessInstance(processInstance.getProcessInstanceId(),"流程被事件提出者关闭");

        event.setStatus(statusEnum.close.getKey());

        this.updateById(event);

    }

    public void approve(String code, String businessId) {

        UserDetails userDetails = JwtTokenUtil.getUser();

        Preconditions.checkNotNull(userDetails.getUsername(), "用户名不能为空");

        Task task = taskService.createTaskQuery().taskCandidateOrAssigned(userDetails.getUsername()).processInstanceBusinessKey(businessId).singleResult();

        Map<String, Object> vars = Maps.newHashMap();

        vars.put("code", code);

        taskService.complete(task.getId(), vars);

        Event event =this.selectById(Long.valueOf(businessId)).get();

        event.setStatus(statusEnum.done.getKey());

        this.updateById(event);
    }

    public void transferToOtherStaff(String userName, String businessId) {

        UserDetails userDetails = JwtTokenUtil.getUser();

        Preconditions.checkNotNull(userDetails.getUsername(), "用户名不能为空");

        Task task = taskService.createTaskQuery().taskCandidateOrAssigned(userDetails.getUsername()).processInstanceBusinessKey(businessId).singleResult();

        taskService.setAssignee(task.getId(), userName);

        Event event = this.selectById(Long.valueOf(businessId)).get();

        event.setStatus(statusEnum.handle.getKey());

        this.updateById(event);

    }

    public void passToparentHandle(String code, String businessId, String userName) {

        UserDetails userDetails = JwtTokenUtil.getUser();

        Preconditions.checkNotNull(userDetails.getUsername(), "用户名不能为空");

        Task task = taskService.createTaskQuery().taskCandidateOrAssigned(userDetails.getUsername()).processInstanceBusinessKey(businessId).singleResult();

        Map<String, Object> vars = Maps.newHashMap();

        vars.put("code", code);

        vars.put("handleEventStaff", userName);

        taskService.complete(task.getId(), vars);

    }

    public void uploadAttachmentFile(@RequestBody MultipartFile file, String id) throws IOException {

        File fileUpload = new File(this.uploadPath + "/" + file.getOriginalFilename());

        file.transferTo(fileUpload);

        EventAttachment eventAttachment = new EventAttachment();

        eventAttachment.setEventIdentifier(id);

        eventAttachment.setAttachmentName(file.getOriginalFilename());

        eventAttachment.setAttachmentPath(fileUpload.getAbsolutePath());

        eventAttachment.setCreatedBy(JwtTokenUtil.getUser().getUsername());

        this.eventAttachmentRepository.save(eventAttachment);

    }

    public void deleteEvent(long id, String eventIdentifier) {

        Preconditions.checkNotNull(id, "事件ID不能为空");

        Preconditions.checkNotNull(eventIdentifier, "事件编码不能为空");
        //通过ID先把问题删除
        this.deleteById(id);
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

    public void deleteUploadFile(String fileUrl, long id) {

        File deleteFile = new File(fileUrl);

        this.eventAttachmentRepository.deleteById(id);

        if (deleteFile.isFile() && deleteFile.exists()) {

            deleteFile.delete();

        }

    }

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

    public BranchInfoVo queryHandleEventGroup(String branCode, String rank) {

        BranchInfoVo branchInfoVo = new BranchInfoVo();

        if (!Strings.isNullOrEmpty(branCode)) {

            //事件创建人选了提出事件机构，查询的上级别处理部门
            String supperBran = this.branchInfoRepository.findByBranCode(branCode).getSupperBran();

            BeanUtils.copyProperties(this.branchInfoRepository.findByBranCode(supperBran), branchInfoVo);

        } else {

            if ("same".equalsIgnoreCase(rank)) {
                //转办查询同级别
                String branchCode = this.userService.queryUserByName(JwtTokenUtil.getUser().getUsername()).getOrgCode();

                BeanUtils.copyProperties(this.branchInfoRepository.findByBranCode(branchCode), branchInfoVo);

            }

            if ("parent".equalsIgnoreCase(rank)) {

                String branchCode = this.userService.queryUserByName(JwtTokenUtil.getUser().getUsername()).getOrgCode();

                String supperBran = this.branchInfoRepository.findByBranCode(branchCode).getSupperBran();
                //移交上级查询上级别部门
                BeanUtils.copyProperties(this.branchInfoRepository.findByBranCode(supperBran), branchInfoVo);

            }

        }

        return branchInfoVo;
    }

    public Map<String, Object> getCurrentOptionResult() {

        Map<String, Object> optionResult = Maps.newHashMap();

        //选出登陆者的上级部门
        String branchCode = this.userRepository.findByUserName(JwtTokenUtil.getUser().getUsername()).getOrgCode();

        BranchInfo branchInfo = this.branchInfoRepository.findByBranCode(this.branchInfoRepository.findByBranCode(branchCode).getSupperBran());

        //选出上级部门下面的所有人员
        List<User> users = this.userRepository.findByOrgCode(branchInfo.getBranCode());

        optionResult.put("handleEventGroups", branchInfo);

        optionResult.put("handleEventStaffs", users);

        return optionResult;
    }

    public PageInfo<EventVo> getHandleEventHistoryRecord(HashMap map) {

        map.put("assignee", JwtTokenUtil.getUser().getUsername());

        PageHelper.startPage((int)map.get("pageNum"), (int)map.get("pageSize"));

        List<Event> events = this.eventMapper.getHandleEventHistoryRecord(map);

        PageInfo<Event> pageInfo = new PageInfo<>(events);

        PageInfo<EventVo> pages = JSON.parseObject(JSON.toJSONString(pageInfo), new TypeReference<PageInfo<EventVo>>() {
        }.getType());

        for (EventVo eventVo : pages.getList()){

            eventVo.setShowInstitution(this.branchInfoRepository.findByBranCode(eventVo.getInstitution()).getBranName());

            eventVo.setShowHandleEventGroup(this.branchInfoRepository.findByBranCode(eventVo.getHandleEventGroup()).getBranName());

            eventVo.setShowHandleEventStaff(this.userService.queryUserByName(eventVo.getHandleEventStaff()).getDisplayName());

            eventVo.setShowPriorityLevel(this.dictionaryRepository.findDictionaryByKey(eventVo.getPriorityLevel()).getName());

            eventVo.setShowEventType(this.dictionaryRepository.findDictionaryByKey(eventVo.getEventType()).getName());

            eventVo.setShowStatus(statusEnum.getEnumByKey(eventVo.getStatus()).getValue());
        }

        return pages;
    }

    public List queryHandleEventDetails(String eventIdentifier, String handleEventStaff) {


        if (Strings.isNullOrEmpty(handleEventStaff)) {

            List<HandleEventDetails> handleEventDetails = this.handleEventDetailsRepository.findByEventIdentifierAndCreatedBy(eventIdentifier,JwtTokenUtil.getUser().getUsername());

            return handleEventDetails;
        } else {

            List<HandleEventDetails> handleEventDetails = this.handleEventDetailsRepository.findByEventIdentifier(eventIdentifier);

            List<HandleEventDetailsVo> handleEventDetailsVos =JSON.parseObject(JSON.toJSONString(handleEventDetails), new TypeReference<List<HandleEventDetailsVo>>(){}.getType());

            handleEventDetailsVos.forEach(handleEventDetailsVo -> handleEventDetailsVo.setShowCreatedBy(this.userService.queryUserByName(handleEventDetailsVo.getCreatedBy()).getDisplayName()));

            return handleEventDetailsVos;
        }

    }

    public HandleEventDetailsVo queryHandleEventDetailsById(Long id) {

        HandleEventDetails handleEventDetails = this.handleEventDetailsService.selectById(id).get();

        HandleEventDetailsVo handleEventDetailsVo = new HandleEventDetailsVo();

        BeanUtils.copyProperties(handleEventDetails, handleEventDetailsVo);

        handleEventDetailsVo.setShowDemand(this.dictionaryRepository.findDictionaryByKey(handleEventDetailsVo.getDemand()).getName());

        handleEventDetailsVo.setShowHandleCategories(this.dictionaryRepository.findDictionaryByKey(handleEventDetailsVo.getHandleCategories()).getName());

        handleEventDetailsVo.setShowOptimizeCategories(this.dictionaryRepository.findDictionaryByKey(handleEventDetailsVo.getOptimizeCategories()).getName());

        if (Objects.nonNull(this.branchInfoRepository.findByBranCode(String.valueOf(handleEventDetailsVo.getHandleEventGroup())))) {

            handleEventDetailsVo.setShowHandleEventGroup(this.branchInfoRepository.findByBranCode(String.valueOf(handleEventDetailsVo.getHandleEventGroup())).getBranName());

        }

        if (!Strings.isNullOrEmpty(handleEventDetailsVo.getHandleEventStaff())) {

            if (Objects.nonNull(this.userService.queryUserByName(handleEventDetailsVo.getHandleEventStaff()))) {

                handleEventDetailsVo.setShowHandleEventStaff(this.userService.queryUserByName(handleEventDetailsVo.getHandleEventStaff()).getDisplayName());

            }
        }

        handleEventDetailsVo.setShowSystem(this.dictionaryRepository.findDictionaryByKey(String.valueOf(handleEventDetailsVo.getSystem())).getName());

        handleEventDetailsVo.setShowTestCover(this.dictionaryRepository.findDictionaryByKey(handleEventDetailsVo.getTestCover()).getName());

        return handleEventDetailsVo;
    }

    public List<BranchInfo> queryAllGroup() {
        return this.branchInfoRepository.findAll();
    }

    public List<User> queryAllStaff() {
        return this.userRepository.findAll();
    }

    @Override
    protected BaseRepository<Event> JpaRepository() {
        return eventRepository;
    }
}
