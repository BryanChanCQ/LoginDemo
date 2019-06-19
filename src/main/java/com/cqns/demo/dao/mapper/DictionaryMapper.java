package com.cqns.demo.dao.mapper;

import com.cqns.demo.dao.entity.Dictionary;
import java.util.List;
/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public interface DictionaryMapper {
    /**
     * 通过ID删除
     * @param id
     * @return
     */
    int deleteByPrimaryKey(Long id);

    /**
     * 插入
     * @param record
     * @return
     */
    int insert(Dictionary record);

    /**
     * 通过ID查询
     * @param id
     * @return
     */
    Dictionary selectByPrimaryKey(Long id);

    /**
     * 查询所有
     * @return
     */
    List<Dictionary> selectAll();

    /**
     * 更新
     * @param record
     * @return
     */
    int updateByPrimaryKey(Dictionary record);
}