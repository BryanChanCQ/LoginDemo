package com.cqns.demo.dao.repository;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.User;


public interface UserRepository extends BaseRepository<User> {
    User findByUsername(String name);
}
