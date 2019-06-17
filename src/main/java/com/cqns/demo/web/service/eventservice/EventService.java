package com.cqns.demo.web.service.eventservice;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.Event;
import com.cqns.demo.dao.entity.EventAttachment;
import com.cqns.demo.dao.repository.BranchInfoRepository;
import com.cqns.demo.dao.repository.DictionaryRepository;
import com.cqns.demo.dao.repository.EventAttachmentRepository;
import com.cqns.demo.dao.repository.EventRepository;
import com.cqns.demo.utils.JwtTokenUtil;
import com.cqns.demo.web.service.AbstractCommonService;
import com.cqns.demo.web.service.baseservice.RoleService;
import com.cqns.demo.web.service.baseservice.UserService;
import com.cqns.demo.web.vo.EventVo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import javax.persistence.criteria.Predicate;

/**
 * @Author BryanChan
 * @Date 2019/5/22 16:53
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Service
public class EventService extends AbstractCommonService<Event>{
    @Resource
    private EventRepository eventRepository;
    @Resource
    private RoleService roleService;
    @Resource
    private UserService userService;
    @Resource
    private DictionaryRepository dictionaryRepository;
    @Resource
    private EventAttachmentRepository eventAttachmentRepository;
    @Resource
    private BranchInfoRepository branchInfoRepository;

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

    @Override
    protected BaseRepository<Event> JpaRepository() {
        return eventRepository;
    }
}
