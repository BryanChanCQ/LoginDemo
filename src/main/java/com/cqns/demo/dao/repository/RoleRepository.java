package com.cqns.demo.dao.repository;

import java.util.List;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.Role;

/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public interface RoleRepository extends BaseRepository<Role> {
	/**
	 * 同ids查询角色
	 * @param ids
	 * @return
	 */
	List<Role> findByIdIn(List<Long> ids);
}
