package com.cqns.demo.dao.repository;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.UserRole;

import java.util.List;

/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public interface UserRoleRepository extends BaseRepository<UserRole> {
    /**
     * 通过用户名查用户角色
     * @param userName
     * @return
     */
    List<UserRole> findByUserName(String userName);

    /**
     * 通过角色ID查询用户角色
     * @param roleId
     * @return
     */
    List<UserRole> findByRoleId(Long roleId);

    /**
     * 通过用户ID查询用户角色
     * @param userId
     * @return
     */
    List<UserRole> findByUserId(Long userId);
}
