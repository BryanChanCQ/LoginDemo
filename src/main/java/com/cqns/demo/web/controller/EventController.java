package com.cqns.demo.web.controller;

import com.cqns.demo.dao.entity.*;
import com.cqns.demo.dao.repository.*;
import com.cqns.demo.utils.JwtTokenUtil;
import com.cqns.demo.utils.ResultInfo;
import com.cqns.demo.utils.statusEnum;
import com.cqns.demo.web.service.baseservice.UserService;
import com.cqns.demo.web.service.eventservice.EventService;
import com.cqns.demo.web.service.eventservice.HandleEventDetailsService;
import com.cqns.demo.web.vo.*;
import com.google.common.base.Preconditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.*;

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
    private DictionaryRepository dictionaryRepository;
    @Resource
    private UserService userService;
    @Resource
    private HandleEventDetailsService handleEventDetailsService;
    @Resource
    private BranchInfoRepository branchInfoRepository;
    @Resource
    private UserRepository userRepository;

    @RequestMapping(value = "/queryInstitution", method = RequestMethod.GET)
    public ResultInfo<BranchInfoVo> queryInstitution() {

        String branchCode = this.userService.queryUserByName(JwtTokenUtil.getUser().getUsername()).getOrgCode();

        BranchInfoVo branchInfoVo = new BranchInfoVo();

        BeanUtils.copyProperties(this.branchInfoRepository.findByBranCode(branchCode), branchInfoVo);

        return ResultInfo.create(BranchInfoVo.class).success(branchInfoVo);
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

        event.setStatus(statusEnum.save.getKey());

        return this.eventService.insert(event) ?
                ResultInfo.create().success().setMsg("保存成功") :
                ResultInfo.create().fail("保存失败");
    }

    @RequestMapping(value = "/submitEvent", method = RequestMethod.POST)
    public void submitEvent(@RequestBody Event event) {

        Preconditions.checkNotNull(event.getId(), "事件Id不能为空");

        this.eventService.submitEvent(event);

    }

    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public ResultInfo<List> find() {

        return ResultInfo.create(List.class).success(this.eventService.findDistrubuteToMe());

    }

    @RequestMapping(value = "/closeProcess", method = RequestMethod.POST)
    public ResultInfo<Void> closeProcess(@RequestBody Event event) {
        try{

            this.eventService.closeProcess(event);

            return ResultInfo.create().success().setMsg("关闭成功");

        }catch (Exception e) {

            logger.error("Error", e);

            return ResultInfo.create().success().setMsg("关闭失败");
        }
    }

    @RequestMapping(value = "/approve", method = RequestMethod.GET)
    public ResultInfo<Void> approve(String code, String businessId) {

        try{

            this.eventService.approve(code, businessId);

            return ResultInfo.create().success().setMsg("处理成功");

        }catch (Exception e) {

            logger.error("Error", e);

            return ResultInfo.create().success().setMsg("处理失败");
        }
    }

    @RequestMapping(value = "/transferToOtherStaff", method = RequestMethod.GET)
    public ResultInfo<Void> transferToOtherStaff(String userName, String businessId) {

        try {

            this.eventService.transferToOtherStaff(userName, businessId);

            return ResultInfo.create().success().setMsg("转办成功");

        }catch (Exception e){

            logger.error("Error", e);

            return ResultInfo.create().success().setMsg("转办失败");
        }

    }

    @RequestMapping(value = "/passToparentHandle", method = RequestMethod.GET)
    public ResultInfo<Void> passToparentHandle(String code, String businessId, String userName) {

        try {

            this.eventService.passToparentHandle(code, businessId, userName);

            return ResultInfo.create().success().setMsg("移交上级成功");

        }catch (Exception e){

            logger.error("Error", e);

            return ResultInfo.create().success().setMsg("移交上级失败");
        }
    }

    @RequestMapping(value = "/uploadAttachmentFile", method = RequestMethod.POST)
    public ResultInfo<Void> uploadAttachmentFile(@RequestBody MultipartFile file, String id) throws IOException {

        try {

            this.eventService.uploadAttachmentFile(file, id);

            return ResultInfo.create().success().setMsg("上传成功");

        }catch (Exception e){

            logger.error("Error", e);

            return ResultInfo.create().success().setMsg("上传失败");
        }

    }

    @RequestMapping(value = "/getUploadFileList", method = RequestMethod.GET)
    public ResultInfo<List> getUploadFileList(String eventIdentifier, String createdBy) {

        return ResultInfo.create(List.class).success(this.eventService.getUploadFileList(eventIdentifier, createdBy));

    }

    @RequestMapping(value = "/deleteEvent", method = RequestMethod.GET)
    public ResultInfo<Void> deleteEvent(long id, String eventIdentifier) {

        try {

            this.eventService.deleteEvent(id, eventIdentifier);

            return ResultInfo.create().success().setMsg("删除成功");

        }catch (Exception e){

            logger.error("Error", e);

            return ResultInfo.create().success().setMsg("删除失败");
        }
    }

    @RequestMapping(value = "/deleteUploadFile", method = RequestMethod.GET)
    public ResultInfo<Void> deleteUploadFile(String fileUrl, long id) {

        try {

            this.eventService.deleteUploadFile(fileUrl, id);

            return ResultInfo.create().success().setMsg("删除成功");

        }catch (Exception e){

            logger.error("Error", e);

            return ResultInfo.create().success().setMsg("删除失败");
        }

    }

    @RequestMapping(value = "/downloadAttachmentFile", method = RequestMethod.GET)
    public void downloadAttachmentFile(String fileUrl, HttpServletResponse response) throws IOException {

        this.eventService.downloadAttachmentFile(fileUrl, response);
    }

    @RequestMapping(value = "/queryDictionary", method = RequestMethod.GET)
    public ResultInfo<List> queryDictionary(String key) {

        return ResultInfo.create(List.class)
                .success(this.dictionaryRepository.findDictionaryByParentId(this.dictionaryRepository.findDictionaryByKey(key).getId()));
    }

    @RequestMapping(value = "/queryHandleEventGroup", method = RequestMethod.GET)
    public ResultInfo<BranchInfoVo> queryHandleEventGroup(String branCode, String rank) {

        return ResultInfo.create(BranchInfoVo.class)
                .success(this.eventService.queryHandleEventGroup(branCode, rank));
    }

    @RequestMapping(value = "/queryHandleEventStaff", method = RequestMethod.GET)
    public ResultInfo<List> queryHandleEventStaff(String branCode) {

        return ResultInfo.create(List.class)
                .success(this.userRepository.findByOrgCode(branCode));
    }

    @RequestMapping(value = "/getCurrentOptionResult", method = RequestMethod.GET)
    public ResultInfo<Map> getCurrentOptionResult() {

        return ResultInfo.create(Map.class)
                .success(this.eventService.getCurrentOptionResult());
    }

    @RequestMapping(value = "/getHandleEventHistoryRecord", method = RequestMethod.GET)
    public ResultInfo<List> getHandleEventHistoryRecord() {

        return ResultInfo.create(List.class)
                .success(this.eventService.getHandleEventHistoryRecord());
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

            return ResultInfo.create(List.class)
                    .success(this.eventService.queryHandleEventDetails(eventIdentifier, handleEventStaff));
    }

    @RequestMapping(value = "/queryHandleEventDetailsById", method = RequestMethod.GET)
    public ResultInfo<HandleEventDetailsVo> queryHandleEventDetailsById(Long id) {

        return ResultInfo.create(HandleEventDetailsVo.class)
                .success(this.eventService.queryHandleEventDetailsById(id));
    }

}
