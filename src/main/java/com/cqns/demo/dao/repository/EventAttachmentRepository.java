package com.cqns.demo.dao.repository;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.EventAttachment;
import com.cqns.demo.dao.entity.Issue;
import com.cqns.demo.dao.entity.IssueAttachment;

import javax.transaction.Transactional;
import javax.validation.constraints.Max;
import java.util.List;

/**
 * @Author BryanChan
 * @Date 2019/5/29 17:19
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Transactional
public interface EventAttachmentRepository extends BaseRepository<EventAttachment> {
    List<EventAttachment> findByEventIdentifier(String eventIdentifier);
    List<EventAttachment> findByEventIdentifierAndAndCreatedBy(String eventIdentifier, String createdBy);
    void deleteEventAttachmentByEventIdentifier(String eventIdentifier);
}
