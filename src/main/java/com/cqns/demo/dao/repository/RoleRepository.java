package com.cqns.demo.dao.repository;

import java.util.List;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.Role;


public interface RoleRepository extends BaseRepository<Role> {
	public List<Role> findByIdIn(List<Long> ids);
}
