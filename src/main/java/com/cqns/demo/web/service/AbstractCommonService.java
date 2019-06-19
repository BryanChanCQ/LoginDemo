package com.cqns.demo.web.service;

import com.cqns.demo.dao.baserepository.BaseRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;

/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public abstract class AbstractCommonService<T> {
    private static Logger logger = LoggerFactory.getLogger(AbstractCommonService.class);
    public boolean insert(T model) {
        try {
            JpaRepository().save(model);
            return true;
        } catch (Exception e){
            logger.error("Error",e);
            return false;
        }

    }
    public boolean deleteById(Long id) {
        try {
            JpaRepository().deleteById(id);
            return true;
        } catch (Exception e){
            logger.error("Error",e);
            return false;
        }
    }
    public boolean updateById(T model) {
        try {
            JpaRepository().saveAndFlush(model);
            return true;
        } catch (Exception e){
            logger.error("Error",e);
            return false;
        }
    }
    public Optional<T> selectById(Long id) {
        return JpaRepository().findById(id);
    }

    /**
     * jpa的Repository
     * @return
     */
    protected abstract BaseRepository<T> JpaRepository();
}
