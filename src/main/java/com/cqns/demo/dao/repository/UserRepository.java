package com.cqns.demo.dao.repository;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.User;

import java.util.List;

/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public interface UserRepository extends BaseRepository<User> {
    /**
     * 通过用户名查询用户
     * @param name
     * @return
     */
    User findByUserName(String name);

    /**
     * 通过机构代码查询用户
     * @param branchCode
     * @return
     */
    List<User> findByOrgCode(String branchCode);
}
