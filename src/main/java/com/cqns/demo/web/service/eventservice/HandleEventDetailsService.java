package com.cqns.demo.web.service.eventservice;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.HandleEventDetails;
import com.cqns.demo.dao.repository.HandleEventDetailsRepository;
import com.cqns.demo.web.service.AbstractCommonService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

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
