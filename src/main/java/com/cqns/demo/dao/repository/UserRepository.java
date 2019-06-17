package com.cqns.demo.dao.repository;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.User;

import java.util.List;


public interface UserRepository extends BaseRepository<User> {
    User findByUserName(String name);
    List<User> findByOrgCode(String branchCode);
}
