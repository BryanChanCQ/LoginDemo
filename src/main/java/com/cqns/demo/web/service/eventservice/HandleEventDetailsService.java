package com.cqns.demo.web.service.eventservice;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.Event;
import com.cqns.demo.dao.entity.EventAttachment;
import com.cqns.demo.dao.entity.HandleEventDetails;
import com.cqns.demo.dao.repository.DictionaryRepository;
import com.cqns.demo.dao.repository.EventAttachmentRepository;
import com.cqns.demo.dao.repository.EventRepository;
import com.cqns.demo.dao.repository.HandleEventDetailsRepository;
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
import javax.persistence.criteria.Predicate;
import java.util.List;
import java.util.Map;

/**
 * @Author BryanChan
 * @Date 2019/5/22 16:53
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Service
public class HandleEventDetailsService extends AbstractCommonService<HandleEventDetails>{
   @Resource
   private HandleEventDetailsRepository handleEventDetailsRepository;

    @Override
    protected BaseRepository<HandleEventDetails> JpaRepository() {
        return this.handleEventDetailsRepository;
    }
}
