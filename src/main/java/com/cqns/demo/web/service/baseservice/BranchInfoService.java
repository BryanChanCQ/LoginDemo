package com.cqns.demo.web.service.baseservice;

import java.util.List;

import javax.annotation.Resource;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.BranchInfo;
import com.cqns.demo.dao.mapper.BranchInfoMapper;
import com.cqns.demo.dao.repository.BranchInfoRepository;
import com.cqns.demo.web.service.AbstractCommonService;
import com.cqns.demo.web.vo.BranchInfoVo;
import com.cqns.demo.web.vo.RoleVo;
import com.google.common.base.Strings;
/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Service
public class BranchInfoService extends AbstractCommonService<BranchInfo> {
	private static Logger logger = LoggerFactory.getLogger(BranchInfoService.class);
	@Resource
	private BranchInfoRepository branchInfoRepository;
	
	@Resource
	private BranchInfoMapper branchInfoMapper;

	@Override
	protected BaseRepository<BranchInfo> JpaRepository() {
		// TODO Auto-generated method stub
		return branchInfoRepository;
	}

	public Page<BranchInfo> getBranchs(BranchInfoVo branchInfo)
	{
		Specification<BranchInfo> specification = new Specification<BranchInfo>() {

            /**
             * 构造断言
             * @param root 实体对象引用
             * @param query 规则查询对象
             * @param cb 规则构建对象
             * @return 断言
             */
            @Override
            public Predicate toPredicate(Root<BranchInfo> root, CriteriaQuery<?> query, CriteriaBuilder cb) {

            	List<Predicate> predicates = Lists.newArrayList(); //所有的断言

				if(!Strings.isNullOrEmpty(branchInfo.getBranName())){ //添加断言

                    Predicate likeNickName = cb.like(root.get("branName").as(String.class) ,branchInfo.getBranName() + "%");

                    predicates.add(likeNickName);
                }

				if (!Strings.isNullOrEmpty(branchInfo.getBranCode())){

					predicates.add(cb.equal(root.get("branCode"), branchInfo.getBranCode()));

				}

				return cb.and(predicates.toArray(new Predicate[predicates.size()]));

            }

		};
        //分页信息
        Pageable pageable = PageRequest.of(branchInfo.getPage() ,branchInfo.getPageSize());
        //查询
        return this.branchInfoRepository.findAll(specification ,pageable);
	}
	
	public BranchInfo updateBranch(BranchInfo branchInfo) {

		return this.branchInfoRepository.saveAndFlush(branchInfo);

	}
	
	public boolean deleteByBranCode(String branCode) {

		try {

			this.branchInfoRepository.deleteByBranCode(branCode);

			return true;
		}catch(Exception e) {

			logger.error("Error", e);

			return false;
		}
	}
	
	public List<BranchInfoVo> getAllBranch() {

		List<BranchInfo> branchList = this.branchInfoRepository.findAll();

		return JSON.parseObject(JSON.toJSONString(branchList), new TypeReference<List<BranchInfoVo>>(){}.getType());
	}
}
