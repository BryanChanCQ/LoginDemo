package com.cqns.demo.dao.repository;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.Issue;
import com.cqns.demo.dao.entity.IssueAttachment;

import javax.validation.constraints.Max;
import java.util.List;

/**
 * @Author BryanChan
 * @Date 2019/5/29 17:19
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public interface IssueAttachmentRepository extends BaseRepository<IssueAttachment> {
    List<IssueAttachment> findByIssueIdentifier(String issueIdentifier);
}
