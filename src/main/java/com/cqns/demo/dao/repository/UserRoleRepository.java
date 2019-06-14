package com.cqns.demo.dao.repository;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.UserRole;

import java.util.List;


public interface UserRoleRepository extends BaseRepository<UserRole> {
    List<UserRole> findByUserName(String userName);
    List<UserRole> findByRoleId(Long roleId);
    List<UserRole> findByUserId(Long userId);
}
