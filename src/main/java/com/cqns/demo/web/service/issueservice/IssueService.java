package com.cqns.demo.web.service.issueservice;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.Issue;
import com.cqns.demo.dao.entity.IssueAttachment;
import com.cqns.demo.dao.entity.Role;
import com.cqns.demo.dao.entity.UserRole;
import com.cqns.demo.dao.repository.IssueAttachmentRepository;
import com.cqns.demo.dao.repository.IssueRepository;
import com.cqns.demo.web.service.AbstractCommonService;
import com.cqns.demo.web.vo.IssueVo;
import com.cqns.demo.web.vo.RoleVo;
import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;
import javax.persistence.criteria.Predicate;

/**
 * @Author BryanChan
 * @Date 2019/5/22 16:53
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Service
public class IssueService extends AbstractCommonService<Issue>{
    @Resource
    private IssueRepository issueRepository;
    @Resource
    private IssueAttachmentRepository issueAttachmentRepository;

    public Page<IssueVo> issueVoPageInfo(IssueVo issueVo){

        Specification specification = (root, criteriaQuery, criteriaBuilder) -> {

            List<Predicate> predicates = Lists.newArrayList();

            if (!Strings.isNullOrEmpty(issueVo.getInstitution())){

                predicates.add(criteriaBuilder.like(root.get("Institution"),"%" + issueVo.getInstitution() + "%"));

            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));

        };

        Pageable pageable = new PageRequest(issueVo.getPage(), issueVo.getPageSize(), Sort.Direction.ASC, "rawUpdateTime");

        Page<IssueVo> page = this.issueRepository.findAll(specification,pageable);


        return page;
    }

    public List<IssueAttachment> getUploadFileList(String issueIdentifier) {

        return this.issueAttachmentRepository.findByIssueIdentifier(issueIdentifier);

    }

    @Override
    protected BaseRepository<Issue> JpaRepository() {
        return issueRepository;
    }
}
