package com.cqns.demo.dao.repository;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.Dictionary;
import java.util.List;

/**
 * @Author BryanChan
 * @Date 2019/6/03 17:05
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public interface DictionaryRepository extends BaseRepository<Dictionary> {
    /**
     * 通过key值查询字典
     * @param key
     * @return
     */
    Dictionary findDictionaryByKey(String key);

    /**
     * 通过父ID查询所有的字典
     * @param parentId
     * @return
     */
    List<Dictionary> findDictionaryByParentId(Long parentId);
}
