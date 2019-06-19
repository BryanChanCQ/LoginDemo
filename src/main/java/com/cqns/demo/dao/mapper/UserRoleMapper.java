package com.cqns.demo.dao.mapper;

import com.cqns.demo.dao.entity.UserRole;

import java.util.List;
/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public interface UserRoleMapper {
    /**
     * 删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(UserRole record);

    /**
     * 查询
     * @param id
     * @return
     */
    UserRole selectByPrimaryKey(Long id);

    /**
     * 查询所有
     * @return
     */
    List<UserRole> selectAll();

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(UserRole record);
}