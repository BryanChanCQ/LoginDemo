package com.cqns.demo.dao.repository;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.Event;
import com.cqns.demo.dao.entity.HandleEventDetails;

import java.util.List;

/**
 * @Author BryanChan
 * @Date 2019/5/22 17:05
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public interface HandleEventDetailsRepository extends BaseRepository<HandleEventDetails> {
    /**
     * 通过事件编码和创建者查询处理详情
     * @param eventIdentifier
     * @param createdBy
     * @return
     */
    List<HandleEventDetails> findByEventIdentifierAndCreatedBy(String eventIdentifier, String createdBy);

    /**
     * 通过事件编码查询处理详情
     * @param eventIdentifier
     * @return
     */
    List<HandleEventDetails> findByEventIdentifier(String eventIdentifier);
}
