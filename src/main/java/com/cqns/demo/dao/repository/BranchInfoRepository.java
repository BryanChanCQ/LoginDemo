package com.cqns.demo.dao.repository;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.BranchInfo;
import com.cqns.demo.web.vo.BranchInfoVo;

import java.util.List;

import javax.transaction.Transactional;

/**
 * @Author BryanChan
 * @Date 2019/6/11 17:19
 * @CreatedFor CRCBank
 * @Version 1.0
 */
@Transactional(rollbackOn = Exception.class)
public interface BranchInfoRepository extends BaseRepository<BranchInfo> {
	/**
	 * 通过机构代码删除机构信息
	 * @param branCode
	 */
	void deleteByBranCode(String branCode);

	/**
	 * 通过机构代码查询机构信息
	 * @param branCode
	 * @return
	 */
	BranchInfo findByBranCode(String branCode);
}
