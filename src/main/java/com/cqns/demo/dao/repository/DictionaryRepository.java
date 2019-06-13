package com.cqns.demo.dao.repository;

import com.cqns.demo.dao.baserepository.BaseRepository;
import com.cqns.demo.dao.entity.Dictionary;
import com.cqns.demo.dao.entity.Issue;

import java.util.List;

/**
 * @Author BryanChan
 * @Date 2019/6/03 17:05
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public interface DictionaryRepository extends BaseRepository<Dictionary> {
    Dictionary findDictionaryByKey(String key);
    List<Dictionary> findDictionaryByParentId(Long parentId);
}
