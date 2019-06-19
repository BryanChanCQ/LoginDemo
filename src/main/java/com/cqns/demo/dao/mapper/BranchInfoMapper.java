package com.cqns.demo.dao.mapper;

import com.cqns.demo.dao.entity.BranchInfo;
import java.util.List;
/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public interface BranchInfoMapper {
    /**
     * 通过机构代码删除机构
     * @param branCode
     * @return
     */
    int deleteByPrimaryKey(String branCode);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(BranchInfo record);

    /**
     * 通过机构代码查询
     * @param branCode
     * @return
     */
    BranchInfo selectByPrimaryKey(String branCode);

    /**
     * 查询所有
     * @return
     */
    List<BranchInfo> selectAll();

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(BranchInfo record);
}