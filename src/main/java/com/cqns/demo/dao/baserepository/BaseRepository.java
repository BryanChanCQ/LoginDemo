package com.cqns.demo.dao.baserepository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
/**
 * @Author BryanChan
 * @Date 2019-06-12 12:34
 * @CreatedFor CRCBank
 * @Version 1.0
 */
public interface BaseRepository<T>  extends JpaRepository<T, Long>,JpaSpecificationExecutor<T> {

}
