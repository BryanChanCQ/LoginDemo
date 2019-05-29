package com.cqns.demo.web.service.issueservice;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.Issue;
import com.cqns.demo.dao.repository.IssueRepository;
import com.cqns.demo.web.service.AbstractCommonService;
import com.cqns.demo.web.vo.IssueVo;
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

    @Override
    protected BaseRepository<Issue> JpaRepository() {
        return issueRepository;
    }
}
