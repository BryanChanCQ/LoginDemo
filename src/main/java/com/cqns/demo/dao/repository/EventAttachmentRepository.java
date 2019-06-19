package com.cqns.demo.dao.repository;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.EventAttachment;
import javax.transaction.Transactional;
import java.util.List;

/**
 * @Author BryanChan
 * @Date 2019/5/29 17:19
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Transactional(rollbackOn = Exception.class)
public interface EventAttachmentRepository extends BaseRepository<EventAttachment> {
    /**
     * 通过事件编码查询事件
     * @param eventIdentifier
     * @return
     */
    List<EventAttachment> findByEventIdentifier(String eventIdentifier);

    /**
     * 通过事件编码和创建者查询附件
     * @param eventIdentifier
     * @param createdBy
     * @return
     */
    List<EventAttachment> findByEventIdentifierAndAndCreatedBy(String eventIdentifier, String createdBy);

    /**
     * 通过事件编码删除附件
     * @param eventIdentifier
     */
    void deleteEventAttachmentByEventIdentifier(String eventIdentifier);
}
